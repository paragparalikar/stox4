package com.stox.module.charting.drawing.region;

import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChartRegionMouseEventHandler implements EventHandler<MouseEvent> {

	private double x;
	private double y;
	private final ChartRegion region;

	@Override
	public void handle(MouseEvent event) {
		if (!event.isConsumed()) {
			if (MouseButton.SECONDARY.equals(event.getButton())) {
				// getChart().getDrawings().remove(Segment.this);
			} else if (MouseButton.PRIMARY.equals(event.getButton())) {
				if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
					x = event.getX();
					y = event.getY();
					event.consume();
				} else if (MouseEvent.MOUSE_DRAGGED.equals(event.getEventType())) {
					region.move(event.getX() - x, event.getY() - y);
					x = event.getX();
					y = event.getY();
					event.consume();
					region.getNode().fireEvent(new UpdatableRequestEvent(region));
				}
			}
		}
	}

}
