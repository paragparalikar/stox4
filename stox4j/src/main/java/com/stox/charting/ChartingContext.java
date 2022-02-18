package com.stox.charting;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.common.scrip.Scrip;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;

@Data
public class ChartingContext {

	private final ObjectProperty<Scrip> scripProperty = new SimpleObjectProperty<>();
	private final ObjectProperty<BarSeries> barSeriesProperty = new SimpleObjectProperty<>(new BaseBarSeries());
	
}
