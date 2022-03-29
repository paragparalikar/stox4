package com.stox.charting.axis.x;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import org.ta4j.core.Bar;

import com.stox.charting.ChartingConfig;
import com.stox.charting.ChartingContext;
import com.stox.charting.crosshair.Crosshair;
import com.stox.charting.grid.VerticalGrid;

import javafx.beans.binding.BooleanBinding;
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
		context.getArgumentsProperty().addListener((o,old,scrip) -> reset());
		context.getBarSeriesProperty().addListener((o,old,value) -> updateCrosshairLabel());
		crosshair.getVerticalLine().endXProperty().addListener((o,old,value) -> updateCrosshairLabel());
		label.visibleProperty().bind(new BooleanBinding() {
			{bind(crosshair.visibleProperty(), crosshair.getVerticalLine().endXProperty());}
			@Override
			protected boolean computeValue() {
				return crosshair.isVisible() && 
						null != context.getBar(getIndex(crosshair.getVerticalLine().getEndX()));
			}
		});
	}
	
	private void updateCrosshairLabel() {
		final double value = crosshair.getVerticalLine().getEndX();
		final int index = getIndex(value);
		final Bar bar = context.getBar(index);
 		final String text = null == bar ? null : dateTimeFormatter.format(bar.getEndTime());
		label.setText(text);
		label.setLayoutX(value - label.getWidth()/2);
	}
	
	private void style() {
		getStyleClass().add("x-axis");
		container.getStyleClass().add("container");
		HBox.setHgrow(container, Priority.ALWAYS);
		filler.getStyleClass().add("filler");
		label.getStyleClass().add("axis-info-label");
	}
	
	public double getX(final int index) {
		return index * unitWidth - unitWidth/2 + panWidth;
	}

	public int getIndex(final double x) {
		return (int)Math.ceil((x - panWidth - unitWidth/2)/unitWidth);
	}

	public int getEndIndex() {
		return getIndex(container.getWidth());
	}
	
	public int getStartIndex() {
		return getIndex(0);
	}
	
	public void pan(final double deltaX) {
		panWidth += deltaX;
	}
	
	public void shift(int barCount) {
		pan(-1 * barCount*unitWidth);
	}
	
	public void reset() {
		panWidth = container.getWidth() - 10 * unitWidth;
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
		final double x = getX(index) + unitWidth/2;
		verticalGrid.addLine(x);
		container.getChildren().add(label);
		label.setLayoutY(0);
		label.layoutXProperty().bind(label.widthProperty().divide(2).subtract(x).negate());
	}
	
	public XAxisState getState() {
		return new XAxisState(unitWidth, panWidth);
	}
	
	public void setState(XAxisState state) {
		if(null == state) return;
		this.unitWidth = state.getUnitWidth();
		this.panWidth = state.getPanWidth();
	}
}
