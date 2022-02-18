package com.stox.charting.axis;

import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.ta4j.core.BarSeries;

import com.stox.charting.ChartingContext;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import lombok.Getter;

@Getter
public class XAxis extends StackPane {
	public static final double HEIGHT = 28;
	private static final Font FONT = Font.font(12);
	private static final Insets INSETS = new Insets(2);

	private final ChartingContext context;
	private final Pane container = new Pane();
	private double unitWidth = 5, maxUnitWidth = 50, minUnitWidth = 1, panWidth;
	
	public XAxis(ChartingContext context) {
		this.context = context;
		context.getScripProperty().addListener((o,old,scrip) -> reset());
		
		setMaxHeight(HEIGHT);
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
		setHeight(HEIGHT);
		
		HBox.setHgrow(container, Priority.ALWAYS);
		final Rectangle rectangle = new Rectangle(YAxis.WIDTH, XAxis.HEIGHT);
		rectangle.setFill(Color.TRANSPARENT);
		getChildren().add(new HBox(container, rectangle));
		setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
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
	}
	
	public void zoom(final double x, final int percentage) {
		double newUnitWidth = unitWidth * (100 + percentage) / 100;
		if (newUnitWidth >= minUnitWidth && newUnitWidth <= maxUnitWidth && newUnitWidth != unitWidth) {
			final double position = (x - panWidth) / unitWidth;
			unitWidth = newUnitWidth;
			panWidth = x - position * unitWidth;
		}
	}
	
	public void layoutChartChildren() {
		final BarSeries barSeries = context.getBarSeriesProperty().get();
		if(null != barSeries) {
			ZonedDateTime lastTimestamp = null;
			container.getChildren().clear();
			final int startIndex = Math.max(getStartIndex(), 0);
			final int endIndex = Math.min(getEndIndex(), barSeries.getBarCount());
			for(int index = endIndex - 1; index >= startIndex; index--) {
				final ZonedDateTime timestamp = barSeries.getBar(index).getEndTime();
				if(null == lastTimestamp || lastTimestamp.getYear() != timestamp.getYear()) {
					addLabel(index, String.valueOf(timestamp.getYear()));
				} else if(lastTimestamp.getMonth() != timestamp.getMonth()) {
					addLabel(index, timestamp.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
				} else if(unitWidth > 18) {
					addLabel(index, String.valueOf(timestamp.getDayOfMonth()));
				}
				lastTimestamp = timestamp;
			}
		}
	}
	
	private void addLabel(int index, String text) {
		final Label label = new Label(text);
		label.setFont(FONT);
		label.setPadding(INSETS);
		container.getChildren().add(label);
		label.relocate(getX(index), 0);
	}
}
