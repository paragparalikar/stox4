package com.stox.charting.plot;

import java.util.function.Supplier;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.unit.Unit;
import com.stox.charting.unit.resolver.HighLowResolver;
import com.stox.common.scrip.Scrip;

public class RulePlot extends Plot<Boolean> {

	public RulePlot(ChartingContext context, Supplier<Unit<Boolean>> unitSupplier,
			HighLowResolver<Boolean> highLowResolver) {
		super(context, unitSupplier, highLowResolver);
	}

	@Override
	public void reload(Scrip scrip, XAxis xAxis) {

	}

}
