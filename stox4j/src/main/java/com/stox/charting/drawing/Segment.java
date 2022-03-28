package com.stox.charting.drawing;

import com.stox.charting.chart.Chart;
import com.stox.charting.drawing.ControlPoint.ControlPointState;
import com.stox.charting.drawing.Segment.SegmentState;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Segment extends Line implements Drawing<SegmentState> {
	
	@Getter
	@RequiredArgsConstructor
	public static class SegmentState implements DrawingState {
		private static final long serialVersionUID = 6146132228535631227L;
		private final ControlPointState startState, endState;
		public SegmentState() { this(new ControlPointState(), new ControlPointState()); }
		public Segment create(Chart chart) { return new Segment(chart, this); }
	}

	private final Chart chart;
	private final SegmentState state;
	private final ControlPoint startPoint, endPoint;
	private final Group node = new Group();
	private double previousX, previousY;
	
	public Segment(Chart chart, SegmentState state) {
		this.chart = chart;
		this.state = state;
		setManaged(false);
		node.setAutoSizeChildren(false);
		getStyleClass().addAll("drawing", "segment");
		
		this.startPoint = state.getStartState().create(chart);
		startXProperty().bindBidirectional(startPoint.centerXProperty());
		startYProperty().bindBidirectional(startPoint.centerYProperty());
		
		this.endPoint = state.getEndState().create(chart);
		endXProperty().bindBidirectional(endPoint.centerXProperty());
		endYProperty().bindBidirectional(endPoint.centerYProperty());
		
		addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
		addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
		this.node.getChildren().addAll(this, startPoint.getNode(), this.endPoint.getNode());
	}
	
	private void onMousePressed(MouseEvent event) {
		if(MouseButton.SECONDARY.equals(event.getButton())) {
			chart.remove(this);
			event.consume();
		} else {
			previousX = event.getX();
			previousY = event.getY();
		}
	}
	
	protected void onMouseDragged(MouseEvent event) {
		dragX(event.getX());
		dragY(event.getY());
		event.consume();
	}
	
	protected void dragX(double x) {
		final double xDiff = previousX - x;
		endPoint.setCenterX(endPoint.getCenterX() - xDiff);
		startPoint.setCenterX(startPoint.getCenterX() - xDiff);
		previousX = x;
	}
	
	protected void dragY(double y) {
		final double yDiff = previousY - y;
		startPoint.setCenterY(startPoint.getCenterY() - yDiff);
		endPoint.setCenterY(endPoint.getCenterY() - yDiff);
		previousY = y;
	}
	
	@Override
	public void draw() {
		endPoint.draw();
		startPoint.draw();
	}
	
	@Override
	public void moveTo(double x, double y) {
		endPoint.moveTo(x, y);
		startPoint.moveTo(x, y);
	}
	
	@Override
	public void dragTo(double x, double y) {
		endPoint.dragTo(x, y);
	}
}
