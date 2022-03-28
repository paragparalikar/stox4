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
		
		addEventFilter(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
		this.node.getChildren().addAll(this, startPoint.getNode(), this.endPoint.getNode());
	}
	
	private void onMousePressed(MouseEvent event) {
		if(MouseButton.SECONDARY.equals(event.getButton())) {
			chart.remove(this);
			event.consume();
		}
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
