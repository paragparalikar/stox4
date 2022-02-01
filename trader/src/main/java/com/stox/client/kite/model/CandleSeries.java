package com.stox.client.kite.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CandleSeries {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	private List<List<Object>> candles;
	private final List<Candle> data = new ArrayList<>();
	
	public List<Candle> getData(){
		if(data.isEmpty() && null != candles) {
			candles.stream()
				.map(this::parse)
				.filter(Objects::nonNull)
				.forEach(data::add);
		}
		return data;
	}
	
	private Candle parse(List<Object> objects) {
		try {
			return Candle.builder()
					.timestamp(ZonedDateTime.parse((String)objects.get(0), formatter))
					.open(Double.parseDouble(objects.get(1).toString()))
					.high(Double.parseDouble(objects.get(2).toString()))
					.low(Double.parseDouble(objects.get(3).toString()))
					.close(Double.parseDouble(objects.get(4).toString()))
					.volume(Integer.parseInt(objects.get(5).toString()))
					.build();
		} catch(Exception e) {
			log.error("Faield to parse candle for dat {}", objects);
			return null;
		}
		
	}
	
}
