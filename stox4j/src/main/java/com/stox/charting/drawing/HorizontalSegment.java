package com.stox.charting.drawing;

import com.stox.charting.chart.Chart;

import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class HorizontalSegment extends Segment {
	
	@Getter
	@RequiredArgsConstructor
	public static class HorizontalSegmentState extends SegmentState {
		private static final long serialVersionUID = 6146132228535631227L;
		public HorizontalSegment create(Chart chart) { return new HorizontalSegment(chart, this); }
	}

	public HorizontalSegment(Chart chart, HorizontalSegmentState state) {
		super(chart, state);
		getStartPoint().centerYProperty().bindBidirectional(getEndPoint().centerYProperty());
	}
	
	@Override
	protected void onMouseDragged(MouseEvent event) {
		dragY(event.getY());
		event.consume();
	}

}
