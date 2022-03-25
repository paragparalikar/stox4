package com.stox.charting.unit;

import org.ta4j.core.Bar;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.unit.parent.UnitParent;
import com.stox.common.util.Colors;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.Setter;

public class CandleUnit extends Group implements Unit<Bar, Node> {

	@Setter private XAxis xAxis;
	@Setter private YAxis yAxis;
	@Setter private ChartingContext context;
	private final Line line = new Line();
	private final Rectangle body = new Rectangle();
	
	public CandleUnit() {
		getChildren().addAll(line, body);
		line.getStyleClass().addAll("candle","wick");
		line.getStyleClass().addAll("candle","body");
		line.strokeProperty().bindBidirectional(body.fillProperty());
		line.endXProperty().bindBidirectional(line.startXProperty());
		line.startXProperty().bind(body.xProperty()
				.add(body.widthProperty().divide(2d))
				.subtract(line.strokeWidthProperty().divide(2)));
	}
	
	@Override
	public void layoutChildren(int index, Bar bar, UnitParent<Node> parent) {
		final double barWidth = xAxis.getUnitWidth() * 0.8;
		body.setX(xAxis.getX(index) + xAxis.getUnitWidth() * 0.1);
		body.setWidth(barWidth);
		final double upperY = yAxis.getY(bar.getOpenPrice().max(bar.getClosePrice()).doubleValue());
		final double lowerY = yAxis.getY(bar.getOpenPrice().min(bar.getClosePrice()).doubleValue());
		body.setY(upperY);
		body.setHeight(lowerY - upperY);
		line.setStartY(yAxis.getY(bar.getHighPrice().doubleValue()));
		line.setEndY(yAxis.getY(bar.getLowPrice().doubleValue()));
		body.setFill(bar.getOpenPrice().isLessThan(bar.getClosePrice()) ? Colors.UP : Colors.DOWN);
		parent.add(this);
	}
	
	@Override
	protected void layoutChildren() {

	}

}
