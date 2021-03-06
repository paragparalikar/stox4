package com.stox.module.charting.drawing.segment;

import com.stox.module.charting.ChartingView;
import com.stox.module.charting.ModeMouseHandler;

import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentModeMouseHandler implements ModeMouseHandler {

	private final ChartingView chartingView;
	private final SegmentModeMouseEventHandler<?> handler;

	@Override
	public void attach() {
		chartingView.getNode().addEventFilter(MouseEvent.MOUSE_PRESSED, handler);
		chartingView.getNode().addEventFilter(MouseEvent.MOUSE_DRAGGED, handler);
		chartingView.getNode().addEventFilter(MouseEvent.MOUSE_RELEASED, handler);
	}

	@Override
	public void detach() {
		chartingView.getNode().removeEventFilter(MouseEvent.MOUSE_PRESSED, handler);
		chartingView.getNode().removeEventFilter(MouseEvent.MOUSE_DRAGGED, handler);
		chartingView.getNode().removeEventFilter(MouseEvent.MOUSE_RELEASED, handler);
	}

}
