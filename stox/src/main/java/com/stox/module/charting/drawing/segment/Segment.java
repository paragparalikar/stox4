package com.stox.module.charting.drawing.segment;

import java.util.Optional;

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

public abstract class Segment<S extends SegmentState> extends AbstractDrawing<S> {

	private final transient Line line = new Line();
	
	@Getter(AccessLevel.PROTECTED)
	private final ControlPoint one = new ControlPoint();
	
	@Getter(AccessLevel.PROTECTED)
	private final ControlPoint two = new ControlPoint();
	
	private final Group node = new FluentGroup(line, one.getNode(), two.getNode()).classes("drawing");

	public Segment() {
		node.setManaged(false);
		node.setAutoSizeChildren(false);
		bind();
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
	
	public S fill(S state) {
		state.one(one.location()).two(two.location());
		return state;
	}
	
	public Segment<S> state(final S state) {
		Optional.ofNullable(state).ifPresent(value -> {
			one.location(state.one());
			two.location(state.two());
		});
		return this;
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
