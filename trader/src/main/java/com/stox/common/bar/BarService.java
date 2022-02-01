package com.stox.common.bar;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BarService {

	private final BarRepository barRepository = new BarRepository();
	
	public List<Bar> find(final String isin, int count) {
		return barRepository.find(isin, count);
	}
	
}
