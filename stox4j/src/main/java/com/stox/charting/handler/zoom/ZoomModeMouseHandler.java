package com.stox.charting.handler.zoom;

import com.stox.charting.chart.Chart;
import com.stox.charting.handler.ModeMouseHandler;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ZoomModeMouseHandler implements ModeMouseHandler {

	private final Chart chart;
	private final EventHandler<ScrollEvent> scrollHandler = event -> scroll(event);

	private void scroll(final ScrollEvent event) {
		if (!event.isConsumed()) {
			event.consume();
			chart.fireEvent(new ZoomRequestEvent(event.getX(), 0 < event.getDeltaY() ? -5 : 5));
		}
	}
	
	@Override
	public void addListeners() {
		removeListeners();
		chart.getContentArea().addEventHandler(ScrollEvent.SCROLL, scrollHandler);
	}

	@Override
	public void removeListeners() {
		if(null != chart) {
			chart.getContentArea().removeEventHandler(ScrollEvent.SCROLL, scrollHandler);
		}
	}

}
