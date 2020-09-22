package com.stox.module.charting.drawing.segment;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SegmentModeMouseEventHandler<T extends Segment<?>> implements EventHandler<MouseEvent> {

	private double x;
	private double y;
	private T segment;

	private final BiConsumer<T, MouseEvent> startCallback;
	private final Consumer<T> endCallback;

	@Override
	public void handle(MouseEvent event) {
		if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
			x = event.getScreenX();
			y = event.getScreenY();
			segment = buildSegment();
			startCallback.accept(segment, event);
		} else if (MouseEvent.MOUSE_DRAGGED.equals(event.getEventType())) {
			final Parent parent = segment.getNode().getParent();
			final Point2D startPoint = parent.screenToLocal(x, y);
			final Point2D endPoint = parent.screenToLocal(event.getScreenX(), event.getScreenY());
			move(segment, startPoint, endPoint);
		} else if (MouseEvent.MOUSE_RELEASED.equals(event.getEventType())) {
			endCallback.accept(segment);
		}
		segment.getNode().fireEvent(new UpdatableRequestEvent(segment));
	}

	protected abstract T buildSegment();

	protected abstract void move(final T segment, final Point2D startPoint, final Point2D endPoint);

}
