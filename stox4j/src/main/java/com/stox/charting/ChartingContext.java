package com.stox.charting;

import java.time.ZonedDateTime;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.common.scrip.Scrip;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

@Getter
public class ChartingContext {
	
	private final ObjectProperty<BarSeries> barSeriesProperty = new SimpleObjectProperty<>(new BaseBarSeries());
	private final ObjectProperty<ChartingArguments> argumentsProperty = new SimpleObjectProperty<>(new ChartingArguments(null, null));
	
	public Scrip getScrip() { 
		return null == argumentsProperty.get() ? null : argumentsProperty.get().getScrip();
	}
	
	public ZonedDateTime getTo() {
		return null == argumentsProperty.get() ? null : argumentsProperty.get().getTo();
	}
	
	public Bar getBar(int index) {
		final BarSeries barSeries = barSeriesProperty.get();
		return null != barSeries && 0 <= index && index < barSeries.getBarCount() ? barSeries.getBar(index) : null;
	}
	
	public int getBarCount() {
		final BarSeries barSeries = barSeriesProperty.get();
		return null == barSeries ? 0 : barSeries.getBarCount();
	}
}