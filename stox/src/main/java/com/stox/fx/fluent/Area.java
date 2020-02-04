package com.stox.fx.fluent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;

public interface Area<A extends Area<A>> {

	double x();

	double y();

	double height();

	double width();

	Cursor cursor();

	A cursor(final Cursor cursor);

	Rectangle2D maxBounds();

	A bounds(double x, double y, double width, double height);

	<T extends Event> A addHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler);

	<T extends Event> A removeHandler(EventType<T> eventType, EventHandler<? super T> eventHandler);

	<T extends Event> A addFilter(final EventType<T> eventType, final EventHandler<? super T> eventHandler);

	<T extends Event> A removeFilter(EventType<T> eventType, EventHandler<? super T> eventHandler);
	
	A getThis();
	
}
