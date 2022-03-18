package com.stox.ml;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

import lombok.Builder;
import smile.data.AbstractTuple;
import smile.data.type.DataType;
import smile.data.type.StructField;
import smile.data.type.StructType;

@Builder
public class BarSeriesTuple extends AbstractTuple {
	private static final long serialVersionUID = -2788136059591500998L;

	private final int index;
	private final int class_;
	private final int barCount;
	private final BarSeries barSeries;
	private StructType structType;
	
	@Override
	public StructType schema() {
		if(null == structType) {
			final DataType doubleDataType = DataType.of(double.class);
			final List<StructField> fields = new ArrayList<>(1 + barCount * 5);
			fields.add(new StructField("class", DataType.of(int.class)));
			for(int index = 0; index < barCount; index++) {
				fields.add(new StructField("open_" + index, doubleDataType));
				fields.add(new StructField("high_" + index, doubleDataType));
				fields.add(new StructField("low_" + index, doubleDataType));
				fields.add(new StructField("close_" + index, doubleDataType));
				fields.add(new StructField("volume_" + index, doubleDataType));
			}
			this.structType = new StructType(fields);
		}
		return structType;
	}

	@Override
	public Object get(int i) {
		if(0 == i) return class_;
		final int barIndex = index + ((i - 1) / 5);
		final Bar bar = barSeries.getBar(barIndex);
		switch(barIndex % 5) {
			case 0: return bar.getOpenPrice().doubleValue();
			case 1: return bar.getHighPrice().doubleValue();
			case 2: return bar.getLowPrice().doubleValue();
			case 3: return bar.getClosePrice().doubleValue();
			case 4: return bar.getVolume().doubleValue();
		}
		return null;
	}

}
