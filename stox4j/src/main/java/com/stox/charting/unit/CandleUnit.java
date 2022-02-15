package com.stox.charting.unit;

import org.ta4j.core.Bar;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

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
		final Num upper = bar.getOpenPrice().max(bar.getClosePrice());
		final Num lower = bar.getOpenPrice().min(bar.getClosePrice());
		final Num height = DoubleNum.valueOf(parentHeight);
		final double upperY = value(upper, highestValue, lowestValue, height);
		final double lowerY = value(lower, highestValue, lowestValue, height);
		body.setY(upperY);
		body.setHeight(lowerY - upperY);
		final double highY = value(bar.getHighPrice(), highestValue, lowestValue, height);
		final double lowY = value(bar.getLowPrice(), highestValue, lowestValue, height);
		line.setStartY(highY);
		line.setEndY(lowY);
		
		body.setFill(bar.getOpenPrice().isLessThan(bar.getClosePrice()) ? 
				Color.GREEN : Color.RED);
	}
	
	private double value(Num value, Num highestValue, Num lowestValue, Num height) {
		return height.minus(value.multipliedBy(height)
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
