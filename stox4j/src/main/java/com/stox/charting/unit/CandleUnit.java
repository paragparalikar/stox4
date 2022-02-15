package com.stox.charting.unit;

import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import com.stox.common.bar.Bar;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CandleUnit extends Group implements Unit<Bar> {

	private final Line line = new Line();
	private final Rectangle body = new Rectangle();
	
	public CandleUnit() {
		getChildren().addAll(line, body);
		line.fillProperty().bindBidirectional(body.fillProperty());
		line.endXProperty().bindBidirectional(line.startXProperty());
		line.startXProperty().bind(body.xProperty().add(body.widthProperty().divide(2d)));
	}
	
	@Override
	public void layoutChildren(Bar bar, 
			Num highestValue, Num lowestValue, double parentHeight,
			int barIndex, int visibleBarCount, double parentWidth) {
		final double x = parentWidth - (((double)barIndex) * parentWidth) / ((double)visibleBarCount);
		final double barWidth = (parentWidth * 0.8) / ((double) visibleBarCount);
		body.setX(x - barWidth/2d);
		body.setWidth(barWidth);
		final Num upper = bar.openPrice().max(bar.closePrice());
		final Num lower = bar.openPrice().min(bar.closePrice());
		final Num height = DoubleNum.valueOf(parentHeight);
		final double upperY = value(upper, highestValue, lowestValue, height);
		final double lowerY = value(lower, highestValue, lowestValue, height);
		body.setY(upperY);
		body.setHeight(upperY - lowerY);
		line.setStartY(value(bar.highPrice(), highestValue, lowestValue, height));
		line.setEndY(value(bar.lowPrice(), highestValue, lowestValue, height));
		
		body.setFill(bar.openPrice().isLessThan(bar.getClosePrice()) ? 
				Color.GREEN : Color.RED);
	}
	
	private double value(Num value, Num highestValue, Num lowestValue, Num height) {
		return lowestValue.plus(value.multipliedBy(height)
				.dividedBy(highestValue.minus(lowestValue)))
				.doubleValue();
	}
	
	@Override
	public Node asNode() {
		return this;
	}
	
	@Override
	protected void layoutChildren() {

	}

}
