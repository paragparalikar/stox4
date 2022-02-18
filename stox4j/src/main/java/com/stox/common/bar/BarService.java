package com.stox.common.bar;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BarService {

	private final BarRepository barRepository;
	
	public List<Bar> find(final String isin, int count) {
		return barRepository.find(isin, count);
	}
	
	public List<Bar> find(String isin, int count, ZonedDateTime offset){
		return barRepository.find(isin, count, offset);
	}
	
}
