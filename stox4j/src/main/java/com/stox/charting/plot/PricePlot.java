package com.stox.charting.plot;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.stox.charting.ChartingView.ChartingConfig;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.chart.PlotInfo;
import com.stox.charting.grid.Crosshair;
import com.stox.charting.unit.CandleUnit;
import com.stox.charting.unit.Unit;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.common.util.Colors;
import com.stox.common.util.Strings;
import com.stox.indicator.BarIndicator;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PricePlot extends Plot<Bar> {

	private final ChartingConfig config;
	private final BarService barService;
	private volatile boolean fullyLoaded, loading;
	private final PricePlotInfoPane infoPane = new PricePlotInfoPane();
	
	public PricePlot(ChartingConfig config, Crosshair crosshair, BarService barService) {
		super(CandleUnit::new);
		this.config = config;
		this.barService = barService;
		crosshair.getVerticalLine().endXProperty().addListener(this::onCrosshairXChanged);
	}
	
	private void onCrosshairXChanged(ObservableValue<? extends Number> observable, Number old, Number value) {
		final int index = getXAxis().getIndex(value.doubleValue());
		infoPane.show(getContext().getBar(index));
	}
	
	@Override
	public PlotInfo<Bar> getInfo() {
		return infoPane;
	}
	
	@Override
	public void setContext(ChartingContext context) {
		super.setContext(context);
		context.getScripProperty().addListener((o,old,scrip) -> {
			loading = false;
			fullyLoaded = false;
			context.getBarSeriesProperty().set(new BaseBarSeries());
			reloadBars();
		});
	}
	
	@Override
	public void reload() {
		final BarSeries barSeries = getContext().getBarSeriesProperty().get();
		if(null != barSeries) setIndicator(new BarIndicator(barSeries));
	}
	
	public void reloadBars() {
		try {
			final Scrip scrip = getContext().getScripProperty().get();
			infoPane.set(scrip);
			if(null != scrip && !loading && !fullyLoaded) {
				loading = true;
				doReload(scrip);
			}
		} finally {
			loading = false;
		}
	}

	private void doReload(Scrip scrip) {
		int count = 0;
		ZonedDateTime to = null;
		final XAxis xAxis = getXAxis();
		final BarSeries barSeries = getContext().getBarSeriesProperty().get(); 
		if(null == barSeries || 0 == barSeries.getBarCount()) {
			to = ZonedDateTime.now().plusDays(1);
			count = Math.max(config.getFetchSize(), xAxis.getEndIndex() - xAxis.getStartIndex());
		} else {
			to = barSeries.getLastBar().getEndTime();
			count = Math.max(config.getFetchSize(), xAxis.getEndIndex() - barSeries.getBarCount());
		}
		final List<Bar> bars = barService.find(scrip.getIsin(), count, to);
		fullyLoaded = bars.isEmpty(); //bars.size() < count;
		final List<Bar> data = barSeries.getBarData();
		data.addAll(bars);
		final BarSeries newBarSeries = new BaseBarSeries(data);
		getContext().getBarSeriesProperty().set(newBarSeries);
		if(!fullyLoaded && xAxis.getEndIndex() > data.size()) doReload(scrip);
	}

	@Override
	public void layoutChartChildren() {
		getXAxis().resetLayout();
		super.layoutChartChildren();
	}
	
	@Override
	protected void layoutUnit(int index, Unit<Bar> unit, Bar model) {
		super.layoutUnit(index, unit, model);
		getXAxis().layoutUnit(index, model);
	}

	@Override
	protected double resolveLowValue(Bar model) {
		return model.getLowPrice().doubleValue();
	}

	@Override
	protected double resolveHighValue(Bar model) {
		return model.getHighPrice().doubleValue();
	}
	
}

class PricePlotInfoPane extends HBox implements PlotInfo<Bar>{
	private final Label nameLabel = new Label();
	private final Label openLabel = new Label();
	private final Label highLabel = new Label();
	private final Label lowLabel = new Label();
	private final Label closeLabel = new Label();
	private final HBox priceInfoContainer = new HBox(
			new HBox(new Label("O "), openLabel), 
			new HBox(new Label("H "), highLabel), 
			new HBox(new Label("L "), lowLabel),
			new HBox(new Label("C "), closeLabel));
	
	public PricePlotInfoPane() {
		getStyleClass().add("plot-info-pane");
		nameLabel.getStyleClass().add("scrip-name");
		priceInfoContainer.getStyleClass().add("plot-info-values");
		getChildren().addAll(nameLabel, priceInfoContainer);
	}
	
	@Override
	public Node getNode() {
		return this;
	}
	
	public void set(Scrip scrip) {
		if(null != scrip) {
			nameLabel.setVisible(true);
			nameLabel.setText(scrip.getName().toUpperCase());
		} else {
			nameLabel.setVisible(false);
		}
	}
	
	@Override
	public void show(Bar bar) {
		if(null != bar) {
			priceInfoContainer.setVisible(true);
			final Color color = bar.getOpenPrice().isLessThan(bar.getClosePrice()) ? Colors.UP : Colors.DOWN;
			openLabel.setTextFill(color);
			highLabel.setTextFill(color);
			lowLabel.setTextFill(color);
			closeLabel.setTextFill(color);
			openLabel.setText(Strings.toString(bar.getOpenPrice().doubleValue()));
			highLabel.setText(Strings.toString(bar.getHighPrice().doubleValue()));
			lowLabel.setText(Strings.toString(bar.getLowPrice().doubleValue()));
			closeLabel.setText(Strings.toString(bar.getClosePrice().doubleValue()));
		} else {
			priceInfoContainer.setVisible(false);
		}
	}
}
