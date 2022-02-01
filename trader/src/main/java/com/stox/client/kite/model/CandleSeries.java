package com.stox.client.kite.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CandleSeries {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	private List<List<Object>> candles;
	private final List<Candle> data = new ArrayList<>();
	
	public List<Candle> getDate(){
		if(data.isEmpty() && null != candles) {
			candles.stream().map(this::parse).forEach(data::add);
		}
		return data;
	}
	
	private Candle parse(List<Object> objects) {
		return Candle.builder()
				.timestamp(ZonedDateTime.parse((String)objects.get(0), formatter))
				.open((Double)objects.get(1))
				.high((Double)objects.get(2))
				.low((Double)objects.get(3))
				.close((Double)objects.get(4))
				.volume((Integer)objects.get(5))
				.build();
	}
	
}
