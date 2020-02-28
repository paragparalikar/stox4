package com.stox.module.charting.drawing.region;

import java.util.Optional;

import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.drawing.AbstractDrawing;
import com.stox.module.charting.drawing.ControlPoint;
import com.stox.module.charting.drawing.Drawing;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class ChartRegion extends AbstractDrawing<ChartRegionState> {

	private final Rectangle rectangle = new Rectangle();
	private final ControlPoint one = new ControlPoint();
	private final ControlPoint two = new ControlPoint();
	private final Group node = new Group(rectangle, one.getNode(), two.getNode());

	public ChartRegion() {
		rectangle.getStyleClass().add("chart-region");
		bind();
	}
	
	protected void bind() {
		super.bind();
		rectangle.xProperty().bind(one.centerXProperty());
		rectangle.yProperty().bind(one.centerYProperty());
		rectangle.widthProperty().bind(two.centerXProperty().subtract(one.centerXProperty()));
		rectangle.heightProperty().bind(two.centerYProperty().subtract(one.centerYProperty()));

		final ChartRegionMouseEventHandler chartRegionMouseEventHandler = new ChartRegionMouseEventHandler(this);
		rectangle.addEventHandler(MouseEvent.MOUSE_PRESSED, chartRegionMouseEventHandler);
		rectangle.addEventHandler(MouseEvent.MOUSE_DRAGGED, chartRegionMouseEventHandler);
	}

	@Override
	public Node getNode() {
		return node;
	}

	@Override
	public void update(XAxis xAxis, YAxis yAxis) {
		one.update(xAxis, yAxis);
		two.update(xAxis, yAxis);
	}
	
	@Override
	public void layoutChartChildren(XAxis xAxis, YAxis yAxis) {
		one.layoutChartChildren(xAxis, yAxis);
		two.layoutChartChildren(xAxis, yAxis);
	}

	public void move(final double xDelta, final double yDelta) {
		move(one.getCenterX() + xDelta, one.getCenterY() + yDelta, two.getCenterX() + xDelta,
				two.getCenterY() + yDelta);
	}

	public void move(final double startX, final double startY, final double endX, final double endY) {
		one.setCenterX(startX);
		one.setCenterY(startY);
		two.setCenterX(endX);
		two.setCenterY(endY);
	}

	@Override
	public ChartRegionState state() {
		return new ChartRegionState().one(one.location()).two(two.location());
	}

	@Override
	public Drawing<ChartRegionState> state(ChartRegionState state) {
		Optional.ofNullable(state).ifPresent(value -> {
			one.location(state.one());
			two.location(state.two());
		});
		return this;
	}
}
