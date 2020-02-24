package com.stox.module.charting.axis;

import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

public interface Updatable {

	void update(final XAxis xAxis, final YAxis yAxis);
	
}
