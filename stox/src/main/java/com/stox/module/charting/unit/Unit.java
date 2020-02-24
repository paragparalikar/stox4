package com.stox.module.charting.unit;

import com.stox.module.charting.Attachable;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

public interface Unit<T> extends Attachable {

	public abstract void update(final int index, final T model, final T previousModel, final XAxis xAxis, final YAxis yAxis);

	@Override
	default void attach() {
		
	}
	
	@Override
	default void detach() {
		
	}
	
}
