package com.stox.fx.fluent.stage;

import javafx.event.EventHandler;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public interface IFluentWindow<T extends Window & IFluentWindow<T>>{

	default double x() {
		return getThis().getX();
	}
	
	default T x(double x) {
		getThis().setX(x);
		return getThis();
	}
	
	default double y() {
		return getThis().getY();
	}
	
	default T y(double y) {
		getThis().setY(y);
		return getThis();
	}
	
	default double width() {
		return getThis().getWidth();
	}
	
	default T width(double width) {
		getThis().setWidth(width);
		return getThis();
	}
	
	default double height() {
		return getThis().getHeight();
	}
	
	default T height(double height) {
		getThis().setHeight(height);
		return getThis();
	}
	
	default boolean focused() {
		return getThis().isFocused();
	}
	
	@SuppressWarnings("deprecation")
	default T focused(boolean value) {
		getThis().setFocused(value);
		return getThis();
	}
	
	default Object userData() {
		return getThis().getUserData();
	}
	
	default T userData(Object userData) {
		getThis().setUserData(userData);
		return getThis();
	}
	
	default double opacity() {
		return getThis().getOpacity();
	}
	
	default T opacity(double value) {
		getThis().setOpacity(value);
		return getThis();
	}
	
	default T onCloseRequest(EventHandler<WindowEvent> eventHandler) {
		getThis().setOnCloseRequest(eventHandler);
		return getThis();
	}
	
	default T onShowing(EventHandler<WindowEvent> eventHandler) {
		getThis().setOnShowing(eventHandler);
		return getThis();
	}
	
	default T onShown(EventHandler<WindowEvent> eventHandler) {
		getThis().setOnShown(eventHandler);
		return getThis();
	}
	
	default T onHiding(EventHandler<WindowEvent> eventHandler) {
		getThis().setOnHiding(eventHandler);
		return getThis();
	}
	
	default T onHidden(EventHandler<WindowEvent> eventHandler) {
		getThis().setOnHidden(eventHandler);
		return getThis();
	}
	
	T getThis();
	
}
