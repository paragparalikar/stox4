package com.stox.module.charting.drawing.segment;

import javax.annotation.PostConstruct;

import com.stox.fx.fluent.scene.layout.FluentGroup;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.drawing.AbstractDrawing;
import com.stox.module.charting.drawing.ControlPoint;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class Segment extends AbstractDrawing {

	private final Line line = new Line();
	@Getter(AccessLevel.PROTECTED)
	private final ControlPoint one = new ControlPoint();
	@Getter(AccessLevel.PROTECTED)
	private final ControlPoint two = new ControlPoint();
	private final Group node = new FluentGroup(line, one, two).classes("drawing");

	public Segment() {
		node.setManaged(false);
		node.setAutoSizeChildren(false);
		bind();
	}

	@PostConstruct
	public void postConstruct() {
		
	}
	
	protected void bind() {
		super.bind();
		line.startXProperty().bind(one.centerXProperty());
		line.startYProperty().bind(one.centerYProperty());
		line.endXProperty().bind(two.centerXProperty());
		line.endYProperty().bind(two.centerYProperty());

		final EventHandler<MouseEvent> mouseEventHandler = new SegmentMoveMouseEventHandler(this);
		node.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEventHandler);
		node.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEventHandler);
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
	
	public abstract void move(final double deltaX, final double deltaY);

	public double getStartValue() {
		return one.getValue();
	}

	public double getEndValue() {
		return two.getValue();
	}

	public long getStartDate() {
		return one.getDate();
	}

	public long getEndDate() {
		return two.getDate();
	}

}
