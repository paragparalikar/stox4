package com.stox.data;

import java.time.ZonedDateTime;
import java.util.List;

import com.stox.common.bar.Bar;
import com.stox.common.bar.Interval;
import com.stox.common.scrip.Scrip;

public interface DataProvider {

	List<Scrip> findAllScrips();
	
	Scrip findScripByIsin(String isin);
	
	List<Bar> findBars(String isin, Interval interval);
	
	List<Bar> findBars(String isin, Interval interval, int count);
	
	List<Bar> findBars(String isin, Interval interval, ZonedDateTime from, ZonedDateTime to);
}
