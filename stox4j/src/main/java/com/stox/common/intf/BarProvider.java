package com.stox.common.intf;

import java.util.List;

import org.ta4j.core.Bar;

public interface BarProvider {

	List<Bar> bars(String isin, int count);
	
	List<Bar> bars(String isin, long from, long to);
	
}
