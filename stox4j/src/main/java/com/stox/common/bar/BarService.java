package com.stox.common.bar;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BarService {

	private final BarRepository barRepository;
	
	public void save(String isin, Bar bar) {
		barRepository.save(isin, bar);
	}
	
	public void save(String isin, Iterable<Bar> bars) {
		barRepository.save(isin, bars);
	}
	
	public List<Bar> find(final String isin, int count) {
		return barRepository.find(isin, count);
	}
	
	public List<Bar> find(String isin, int count, ZonedDateTime offset){
		return barRepository.find(isin, count, offset);
	}
	
	public ZonedDateTime readLastDownloadDate() {
		return barRepository.readLastDownloadDate();
	}
	
	public void writeLastDownloadDate(ZonedDateTime date) {
		barRepository.writeLastDownloadDate(date);
	}
}
