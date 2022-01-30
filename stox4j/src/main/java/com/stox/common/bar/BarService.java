package com.stox.common.bar;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarService {

	private final BarRepository barRepository;
	
	public List<Bar> find(final String isin, int count) {
		return barRepository.find(isin, count);
	}
	
}
