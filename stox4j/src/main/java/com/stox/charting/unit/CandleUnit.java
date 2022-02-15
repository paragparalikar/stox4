package com.stox.charting.unit;

import org.ta4j.core.Bar;

import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

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
		line.strokeProperty().bindBidirectional(body.fillProperty());
		line.endXProperty().bindBidirectional(line.startXProperty());
		line.startXProperty().bind(body.xProperty().add(body.widthProperty().divide(2d)));
	}
	
	@Override
	public void layoutChildren(int index, Bar bar, XAxis xAxis, YAxis yAxis) {
		final double barWidth = xAxis.getUnitWidth() * 0.8;
		body.setX(xAxis.getX(index) - barWidth/2d);
		body.setWidth(barWidth);
		final double upperY = yAxis.value(bar.getOpenPrice().max(bar.getClosePrice()));
		final double lowerY = yAxis.value(bar.getOpenPrice().min(bar.getClosePrice()));
		body.setY(upperY);
		body.setHeight(lowerY - upperY);
		line.setStartY(yAxis.value(bar.getHighPrice()));
		line.setEndY(yAxis.value(bar.getLowPrice()));
		
		body.setFill(bar.getOpenPrice().isLessThan(bar.getClosePrice()) ? 
				Color.GREEN : Color.RED);
	}
	
	@Override
	public Node asNode() {
		return this;
	}
	
	@Override
	protected void layoutChildren() {

	}

}
