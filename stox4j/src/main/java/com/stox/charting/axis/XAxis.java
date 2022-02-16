package com.stox.charting.axis;

import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.ta4j.core.BarSeries;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
	public static final double HEIGHT = 16;
	private static final Font FONT = Font.font(8);
	private static final Insets INSETS = new Insets(2);
	

	private final Pane container = new Pane(); 
	private double unitWidth = 10, maxUnitWidth = 50, minUnitWidth = 1, panWidth;
	
	public XAxis() {
		setMaxHeight(HEIGHT);
		setPrefHeight(HEIGHT);
		setMinHeight(HEIGHT);
		setHeight(HEIGHT);
		
		HBox.setHgrow(container, Priority.ALWAYS);
		final Rectangle rectangle = new Rectangle(YAxis.WIDTH, XAxis.HEIGHT);
		rectangle.setFill(Color.TRANSPARENT);
		getChildren().add(new HBox(container, rectangle));
	}
	
	public double getX(final int index) {
		return getWidth() + panWidth - index * unitWidth;
	}

	public int getIndex(final double x) {
		return (int)Math.ceil((getWidth() + panWidth - x) / unitWidth);
	}

	public int getEndIndex() {
		return (int) ((panWidth + getWidth()) / unitWidth);
	}
	
	public int getStartIndex() {
		final double margin = YAxis.WIDTH + 2 * unitWidth;
		return (int) ((panWidth + margin) / unitWidth);
	}
	
	public void pan(final double deltaX) {
		panWidth += deltaX;
	}
	
	public void reset() {
		panWidth = -100;
	}
	
	public void zoom(final double x, final int percentage) {
		double newUnitWidth = unitWidth * (100 + percentage) / 100;
		if (newUnitWidth >= minUnitWidth && newUnitWidth <= maxUnitWidth && newUnitWidth != unitWidth) {
			final double position = (x - panWidth) / unitWidth;
			unitWidth = newUnitWidth;
			panWidth = x - position * unitWidth;
		}
	}
	
	public void layoutChartChildren(BarSeries barSeries) {
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
	
	private void addLabel(int index, String text) {
		final Label label = new Label(text);
		label.setFont(FONT);
		label.setPadding(INSETS);
		container.getChildren().add(label);
		label.relocate(getX(index), 0);
	}
}
