package com.stox.core.model.intf;

import java.util.List;

import com.stox.core.model.Bar;
import com.stox.core.model.BarSpan;

public interface BarProvider {

	List<Bar> get(String isin, BarSpan barSpan, int count);
	
	List<Bar> get(String isin, BarSpan barSpan, long from, long to);
	
}
