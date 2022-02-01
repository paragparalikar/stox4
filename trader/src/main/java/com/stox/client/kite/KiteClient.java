package com.stox.client.kite;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.javalite.http.Get;
import org.javalite.http.Http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stox.client.kite.model.CandleSeries;
import com.stox.client.kite.model.Exchange;
import com.stox.client.kite.model.Holding;
import com.stox.client.kite.model.Instrument;
import com.stox.client.kite.model.Margin;
import com.stox.client.kite.model.Order;
import com.stox.client.kite.model.OrderId;
import com.stox.client.kite.model.OrderType;
import com.stox.client.kite.model.OrderValidity;
import com.stox.client.kite.model.OrderVariety;
import com.stox.client.kite.model.Position;
import com.stox.client.kite.model.Profile;
import com.stox.client.kite.model.Response;
import com.stox.client.kite.util.KiteConstant;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class KiteClient {
	
	@SneakyThrows
	public static List<Instrument> getInstruments(final Exchange exchange){
		final InputStream content = new Get(KiteConstant.URL_INSTRUMENTS + exchange.name(), 
				KiteConstant.TIMEOUT, KiteConstant.TIMEOUT).getInputStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		return reader.lines().map(Instrument::new).collect(Collectors.toList());
	}

	private final KiteSession session;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(KiteConstant.FORMAT_TIMESTAMP);
	
	@SneakyThrows
	public CandleSeries getData(int instrumentToken, String interval, ZonedDateTime from, ZonedDateTime to){
		final String url = String.join("/", KiteConstant.URL_BARS, String.valueOf(instrumentToken), interval) ;
		
		final Map<String, String> queryParams = new HashMap<>();
		queryParams.put("oi", "1");
		queryParams.put("to", formatter.format(to));
		queryParams.put("from", formatter.format(from));
		queryParams.put("user_id", session.getCredentials().getUsername());
		
		final String content = session.get(url + "?" + Http.map2URLEncoded(queryParams)).text();
		final Response<CandleSeries> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<CandleSeries>>(){});
		return response.getData();
	}
	
	@SneakyThrows
	public Profile getProfile() {
		final String content = session.get(KiteConstant.URL_PROFILE).text();
		final Response<Profile> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<Profile>>(){});
		return response.getData();
	}
	
	@SneakyThrows
	public Margin getMargin() {
		final String content = session.get(KiteConstant.URL_MARGIN).text();
		final Response<Margin> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<Margin>>(){});
		return response.getData();
	}
	
	@SneakyThrows
	public List<Holding> getHoldings(){
		final String content = session.get(KiteConstant.URL_HOLDINGS).text();
		final Response<List<Holding>> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<List<Holding>>>(){});
		return Optional.ofNullable(response.getData()).orElse(Collections.emptyList());
	}
	
	@SneakyThrows
	public List<Position> getPositions(){
		final String content = session.get(KiteConstant.URL_POSITIONS).text();
		final Response<List<Position>> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<List<Position>>>(){});
		return Optional.ofNullable(response.getData()).orElse(Collections.emptyList());
	}
	
	@SneakyThrows
	public List<Order> getOrders(){
		final InputStream content = session.get(KiteConstant.URL_ORDERS).getInputStream();
		final Response<List<Order>> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<List<Order>>>(){});
		return Optional.ofNullable(response.getData()).orElse(Collections.emptyList());
	}
	
	@SneakyThrows
	public String createOrder(@NonNull final Order order) {
		final InputStream content = session.post(KiteConstant.URL_ORDERS + "/" + order.getVariety().name())
				.param("tradingsymbol", order.getTradingsymbol())
				.param("exchange", order.getExchange().name())
				.param("transaction_type", order.getTransactionType().name())
				.param("order_type", order.getOrderType().name())
				.param("quantity", String.valueOf(order.getQuantity()))
				.param("product", order.getProduct().name())
				.param("validity", order.getValidity().name())
				.getInputStream();
		final Response<OrderId> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<OrderId>>(){});
		return response.getData().getOrderId();
	}
	
	@SneakyThrows
	public String update(@NonNull final Order order) {
		return update(order.getVariety(), order.getOrderId(), order.getOrderType(), order.getQuantity(), order.getValidity());
	}
	
	@SneakyThrows
	public String update(@NonNull final OrderVariety variety, @NonNull final String orderId, 
			@NonNull final OrderType orderType, int quantity, @NonNull final OrderValidity validity) {
		final InputStream content = session.post(KiteConstant.URL_ORDERS + "/" + variety.name() + "/" + orderId)
				.param("order_type", orderType.name())
				.param("quantity", String.valueOf(quantity))
				.param("validity", validity.name())
				.getInputStream();
		final Response<OrderId> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<OrderId>>(){});
		return response.getData().getOrderId();
	}
	
	@SneakyThrows
	public String cancel(@NonNull final Order order) {
		return cancel(order.getVariety(), order.getOrderId());
	}
	
	@SneakyThrows
	public String cancel(@NonNull final OrderVariety variety, @NonNull final String orderId) {
		final InputStream content = session.delete(KiteConstant.URL_ORDERS + "/" + variety.name() + "/" + orderId)
				.getInputStream();
		final Response<OrderId> response = KiteConstant.JSON.readValue(content, new TypeReference<Response<OrderId>>(){});
		return response.getData().getOrderId();
	}
	
}
