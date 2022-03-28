package com.stox.charting.drawing;

import com.stox.charting.chart.Chart;

import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class VerticalSegment extends Segment {
	
	@Getter
	@RequiredArgsConstructor
	public static class VerticalSegmentState extends SegmentState {
		private static final long serialVersionUID = 6146132228535631227L;
		public VerticalSegment create(Chart chart) { return new VerticalSegment(chart, this); }
	}

	public VerticalSegment(Chart chart, VerticalSegmentState state) {
		super(chart, state);
		getStartPoint().centerXProperty().bindBidirectional(getEndPoint().centerXProperty());
	}
	
	@Override
	protected void onMouseDragged(MouseEvent event) {
		dragX(event.getX());
		event.consume();
	}

}
