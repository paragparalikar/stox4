package com.stox.data;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;

import com.stox.common.scrip.Scrip;

public interface DataProvider {
	
	public List<Scrip> findAll();
	
	public List<Bar> findByIsin(String isin, int count);
	
	public List<Bar> findByIsin(String isin, int count, ZonedDateTime offset);
	
}
