package com.stox.fx.widget.handler;

import java.util.concurrent.atomic.AtomicReference;

import com.stox.fx.fluent.Area;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.FluentRegion;
import com.stox.fx.fluent.scene.layout.IFluentBorderPane;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResizeMouseEventHandler implements EventHandler<MouseEvent> {

	enum ResizeMode {
		TOP,
		TOP_RIGHT,
		TOP_LEFT,
		RIGHT,
		BOTTOM_RIGHT,
		BOTTOM,
		BOTTOM_LEFT,
		LEFT;
	}

	public static <T extends BorderPane & IFluentBorderPane<T>> T resizable(T wrapper) {
		return resizable(wrapper, wrapper);
	}
	
	public static <T extends BorderPane & IFluentBorderPane<T>> T resizable(T wrapper, Area<?> area) {
		final AtomicReference<ResizeMode> resizeReference = new AtomicReference<>();
		final FluentRegion top = new FluentRegion().classes("border", "top").cursor(Cursor.N_RESIZE).fullWidth().addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.TOP));
		final FluentRegion topRight = new FluentRegion().classes("border", "top", "right").cursor(Cursor.NE_RESIZE).addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.TOP_RIGHT));
		final FluentRegion right = new FluentRegion().classes("border", "right").cursor(Cursor.E_RESIZE).addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.RIGHT));
		final FluentRegion bottomRight = new FluentRegion().classes("border", "bottom", "right").cursor(Cursor.SE_RESIZE).addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.BOTTOM_RIGHT));
		final FluentRegion bottom = new FluentRegion().classes("border", "bottom").fullWidth().cursor(Cursor.S_RESIZE).addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.BOTTOM));
		final FluentRegion bottomLeft = new FluentRegion().classes("border", "bottom", "left").cursor(Cursor.SW_RESIZE).addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.BOTTOM_LEFT));
		final FluentRegion left = new FluentRegion().classes("border", "left").cursor(Cursor.W_RESIZE).addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.LEFT));
		final FluentRegion topLeft = new FluentRegion().classes("border", "top", "left").cursor(Cursor.NW_RESIZE).addHandler(MouseEvent.MOUSE_PRESSED,
				event -> resizeReference.set(ResizeMode.TOP_LEFT));
		wrapper
				.left(left)
				.right(right)
				.top(new FluentHBox(topLeft, top, topRight))
				.bottom(new FluentHBox(bottomLeft, bottom, bottomRight))
				.addHandler(MouseEvent.MOUSE_DRAGGED, new ResizeMouseEventHandler(area, resizeReference))
				.addHandler(MouseEvent.MOUSE_RELEASED, event -> resizeReference.set(null));
		return wrapper;
	}

	private final Area<?> area;
	private final AtomicReference<ResizeMode> modeReference;

	@Override
	public void handle(MouseEvent event) {
		if (null != modeReference.get()) {
			switch (modeReference.get()) {
				case BOTTOM:
					area.bounds(area.x(), area.y(), area.width(), event.getY());
					break;
				case BOTTOM_LEFT:
					area.bounds(area.x() + event.getX(), area.y(), area.width() - event.getX(), event.getY());
					break;
				case BOTTOM_RIGHT:
					area.bounds(area.x(), area.y(), event.getX(), event.getY());
					break;
				case LEFT:
					area.bounds(area.x() + event.getX(), area.y(), area.width() - event.getX(), area.height());
					break;
				case RIGHT:
					area.bounds(area.x(), area.y(), event.getX(), area.height());
					break;
				case TOP:
					area.bounds(area.x(), area.y() + event.getY(), area.width(), area.height() - event.getY());
					break;
				case TOP_LEFT:
					area.bounds(area.x() + event.getX(), area.y() + event.getY(), area.width() - event.getX(), area.height() - event.getY());
					break;
				case TOP_RIGHT:
					area.bounds(area.x(), area.y() + event.getY(), event.getX(), area.height() - event.getY());
					break;
				default:
					break;
			}
		}
	}

}
