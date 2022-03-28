package com.stox.charting.drawing;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.drawing.ControlPoint.ControlPointState;
import com.stox.charting.drawing.Segment.SegmentState;

import javafx.scene.Group;
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
		
		@Override
		public Segment create(XAxis xAxis, YAxis yAxis, ChartingContext context) {
			return new Segment(xAxis, yAxis, context, this);
		}
		
	}

	private final SegmentState state;
	private final ControlPoint startPoint, endPoint;
	private final Group node = new Group();
	
	public Segment(XAxis xAxis, YAxis yAxis, ChartingContext context, SegmentState state) {
		this.state = state;
		setManaged(false);
		node.setAutoSizeChildren(false);
		getStyleClass().addAll("drawing", "segment");
		
		this.startPoint = state.getStartState().create(xAxis, yAxis, context);
		startXProperty().bindBidirectional(startPoint.centerXProperty());
		startYProperty().bindBidirectional(startPoint.centerYProperty());
		
		this.endPoint = state.getEndState().create(xAxis, yAxis, context);
		endXProperty().bindBidirectional(endPoint.centerXProperty());
		endYProperty().bindBidirectional(endPoint.centerYProperty());
		
		this.node.getChildren().addAll(this, startPoint.getNode(), this.endPoint.getNode());
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
}
