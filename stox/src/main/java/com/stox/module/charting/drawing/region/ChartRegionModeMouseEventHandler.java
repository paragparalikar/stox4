package com.stox.module.charting.drawing.region;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChartRegionModeMouseEventHandler implements EventHandler<MouseEvent> {

	private double x;
	private double y;
	private ChartRegion region;

	private final BiConsumer<ChartRegion, MouseEvent> startCallback;
	private final Consumer<ChartRegion> endCallback;

	@Override
	public void handle(MouseEvent event) {
		if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
			x = event.getScreenX();
			y = event.getScreenY();
			region = new ChartRegion();
			startCallback.accept(region, event);
		} else if (MouseEvent.MOUSE_DRAGGED.equals(event.getEventType())) {
			final Parent parent = region.getNode().getParent();
			final Point2D startPoint = parent.screenToLocal(x, y);
			final Point2D endPoint = parent.screenToLocal(event.getScreenX(), event.getScreenY());
			region.move(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		} else if (MouseEvent.MOUSE_RELEASED.equals(event.getEventType())) {
			endCallback.accept(region);
		}
		region.getNode().fireEvent(new UpdatableRequestEvent(region));
	}

}
