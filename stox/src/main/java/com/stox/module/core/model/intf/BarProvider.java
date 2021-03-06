package com.stox.module.core.model.intf;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;

public interface BarProvider {

	List<Bar> bars(String isin, BarSpan barSpan, int count);
	
	List<Bar> bars(String isin, BarSpan barSpan, long from, long to);
	
}
