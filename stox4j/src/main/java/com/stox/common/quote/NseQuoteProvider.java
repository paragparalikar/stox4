package com.stox.common.quote;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.zip.GZIPInputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stox.common.scrip.Scrip;
import com.stox.common.util.Http;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class NseQuoteProvider implements QuoteProvider {
	
	private final ObjectMapper objectMapper;
	private final CookieManager cookieManager = new CookieManager();
	private final HttpClient httpClient = HttpClient.newBuilder()
			.cookieHandler(cookieManager)
			.followRedirects(Redirect.ALWAYS)
			.build();

	private void init() throws IOException, InterruptedException, URISyntaxException {
		if(cookieManager.getCookieStore().getCookies().isEmpty()) {
			final HttpRequest.Builder builder = Http.addHeaders(HttpRequest.newBuilder(new URI("https://www.nseindia.com")).GET());
			httpClient.send(builder.build(), BodyHandlers.discarding());
		}
	}
	
	@Override
	@SneakyThrows
	public Quote get(Scrip scrip) {
		init();
		final URI uri = new URI("https://www.nseindia.com/api/quote-equity?symbol=" + scrip.getCode());
		final HttpRequest.Builder builder = Http.addHeaders(HttpRequest.newBuilder(uri).GET());
		final HttpResponse<InputStream> response = httpClient.send(builder.build(), BodyHandlers.ofInputStream());
		final JsonNode node = objectMapper.readTree(new GZIPInputStream(response.body()));
		return new Quote(scrip, node.get("priceInfo").get("lastPrice").doubleValue());
	}
	
}
