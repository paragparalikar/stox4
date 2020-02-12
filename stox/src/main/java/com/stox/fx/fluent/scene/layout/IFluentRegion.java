package com.stox.fx.fluent.scene.layout;

import com.stox.fx.fluent.Area;
import com.stox.fx.fluent.scene.IFluentNode;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.layout.Region;

public interface IFluentRegion<T extends Region & IFluentRegion<T>> extends IFluentNode<T>, Area<T> {

	default T height(double value) {
		getThis().setMinHeight(value);
		getThis().setPrefHeight(value);
		getThis().setMaxHeight(value);
		return getThis();
	}

	default T width(double value) {
		getThis().setMinWidth(value);
		getThis().setPrefWidth(value);
		getThis().setMaxWidth(value);
		return getThis();
	}

	@Override
	default T fullHeight() {
		getThis().setMaxHeight(Double.MAX_VALUE);
		return IFluentNode.super.fullHeight();
	}

	@Override
	default T fullWidth() {
		getThis().setMaxWidth(Double.MAX_VALUE);
		return IFluentNode.super.fullWidth();
	}

	default T padding(Insets value) {
		getThis().setPadding(value);
		return getThis();
	}

	default T padding(double value) {
		return padding(new Insets(value));
	}
	
	default T snapToPixed(boolean value){
		getThis().setSnapToPixel(value);
		return getThis();
	}
	
	@Override
	default Cursor cursor() {
		return IFluentNode.super.cursor();
	}
	
	@Override
	default T cursor(Cursor cursor) {
		return IFluentNode.super.cursor(cursor);
	}
	
	@Override
	default double height() {
		return getThis().getHeight();
	}
	
	default double prefHeight() {
		return getThis().getPrefHeight();
	}
	
	default double minHeight() {
		return getThis().getMinHeight();
	}
	
	default double maxHeight() {
		return getThis().getMaxHeight();
	}
	
	@Override
	default double width() {
		return getThis().getWidth();
	}
	
	default double prefWidth() {
		return getThis().getPrefWidth();
	}
	
	default double minWidth() {
		return getThis().getMinWidth();
	}
	
	default double maxWidth() {
		return getThis().getMaxWidth();
	}
	
	@Override
	default T bounds(double x, double y, double width, double height) {
		layoutX(x).layoutY(y).width(width).height(height).autosize();
		return getThis();
	}

	@Override
	default Rectangle2D maxBounds() {
		final Bounds bounds = getThis().getParent().getLayoutBounds();
		return new Rectangle2D(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
	}
	
	@Override
	default <V extends Event> T addFilter(EventType<V> eventType, EventHandler<? super V> eventFilter) {
		return IFluentNode.super.addFilter(eventType, eventFilter);
	}
	
	@Override
	default <V extends Event> T addHandler(EventType<V> eventType, EventHandler<? super V> eventHandler) {
		return IFluentNode.super.addHandler(eventType, eventHandler);
	}
	
	@Override
	default <V extends Event> T removeFilter(EventType<V> eventType, EventHandler<? super V> eventFilter) {
		return IFluentNode.super.removeFilter(eventType, eventFilter);
	}
	
	@Override
	default <V extends Event> T removeHandler(EventType<V> eventType, EventHandler<? super V> eventHandler) {
		return IFluentNode.super.removeHandler(eventType, eventHandler);
	}
	
	@Override
	default double x() {
		return getThis().getLayoutX();
	}
	
	@Override
	default double y() {
		return getThis().getLayoutY();
	}
	
}
