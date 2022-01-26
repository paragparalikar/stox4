package com.stox.common.bar;

import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarService {

	private final BarRepository barRepository;
	
	public BarSeries find(final String isin, int count) {
		return barRepository.find(isin, count);
	}
	
}
