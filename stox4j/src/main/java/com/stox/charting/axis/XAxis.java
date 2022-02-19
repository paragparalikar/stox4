package com.stox.charting.axis;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;

import com.stox.charting.ChartingView.ChartingConfig;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.grid.Crosshair;
import com.stox.charting.grid.VerticalGrid;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import lombok.Builder;
import lombok.Getter;

@Getter
public class XAxis extends StackPane {

	private final Crosshair crosshair;
	private final ChartingConfig config;
	private final ChartingContext context;
	private final VerticalGrid verticalGrid;
	private final Label label = new Label();
	private final Pane container = new Pane();
	private final Region filler = new Region();
	private double unitWidth = 5, panWidth = 0;
	private volatile ZonedDateTime lastTimestamp = null;
	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	
	@Builder
	public XAxis(ChartingContext context, ChartingConfig config, 
			Crosshair crosshair, VerticalGrid verticalGrid) {
		this.config = config;
		this.context = context;
		this.crosshair = crosshair;
		this.verticalGrid = verticalGrid;
		getChildren().addAll(new HBox(container, filler), new Pane(label));
		bind();
		style();
	}
	
	private void bind() {
		label.visibleProperty().bind(crosshair.visibleProperty());
		context.getScripProperty().addListener((o,old,scrip) -> reset());
		crosshair.getVerticalLine().endXProperty().addListener(this::onCrosshairXChanged);
	}
	
	private void onCrosshairXChanged(ObservableValue<? extends Number> observable, Number old, Number value) {
		if(null != value) {
			label.setText(computeLabelText(value.doubleValue()));
			label.setLayoutX(value.doubleValue() - label.getWidth()/2);
		}
	}
		
	private String computeLabelText(double x) {
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != barSeries && 0 < barSeries.getBarCount()) {
			final int index = getIndex(x);
			if(index > 0 && index < barSeries.getBarCount()) {
				final ZonedDateTime timestamp = barSeries.getBar(index).getEndTime();
				return dateTimeFormatter.format(timestamp);
			}
		}
		return null;
	}
	
	private void style() {
		getStyleClass().add("x-axis");
		container.getStyleClass().add("container");
		HBox.setHgrow(container, Priority.ALWAYS);
		filler.getStyleClass().add("filler");
		label.getStyleClass().add("axis-info-label");
	}
	
	public double getX(final int index) {
		return container.getWidth() + panWidth - index * unitWidth;
	}

	public int getIndex(final double x) {
		return (int)Math.ceil((container.getWidth() + panWidth - x) / unitWidth);
	}

	public int getEndIndex() {
		return (int) ((panWidth + container.getWidth()) / unitWidth);
	}
	
	public int getStartIndex() {
		return (int) Math.ceil((panWidth) / unitWidth);
	}
	
	public void pan(final double deltaX) {
		panWidth += deltaX;
	}
	
	public void reset() {
		panWidth = -10 * unitWidth;
		resetLayout();
	}
	
	public void zoom(final double x, final int percentage) {
		final double maxUnitWidth = config.getMaxUnitWidthProperty();
		final double minUnitWidth = config.getMinUnitWidthProperty();
		final double newUnitWidth = unitWidth * (100 + percentage) / 100;
		if (newUnitWidth >= minUnitWidth && newUnitWidth <= maxUnitWidth && newUnitWidth != unitWidth) {
			final double position = (x - panWidth) / unitWidth;
			unitWidth = newUnitWidth;
			panWidth = x - position * unitWidth;
		}
	}
	
	public void resetLayout() {
		lastTimestamp = null;
		verticalGrid.reset();
		container.getChildren().clear();
	}
	
	public void layoutUnit(int index, Bar bar) {
		final ZonedDateTime timestamp = bar.getEndTime();
		if(null == lastTimestamp || lastTimestamp.getYear() != timestamp.getYear()) {
			addLabel(index, String.valueOf(timestamp.getYear()));
		} else if(lastTimestamp.getMonth() != timestamp.getMonth() && unitWidth > 4) {
			addLabel(index, timestamp.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
		} else if(unitWidth > 24) {
			addLabel(index, String.valueOf(timestamp.getDayOfMonth()));
		}
		lastTimestamp = timestamp;
	}
	
	private void addLabel(int index, String text) {
		final Label label = new Label(text);
		final double x = getX(index);
		verticalGrid.addLine(x);
		container.getChildren().add(label);
		label.setLayoutY(0);
		label.layoutXProperty().bind(label.widthProperty().divide(2).subtract(x).negate());
	}
}
