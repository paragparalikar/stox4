package com.stox.fx.fluent.scene.control;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;

public interface IFluentTab<T extends Tab & IFluentTab<T>> extends IFluentStyleable<T>{
	
	T getThis();
	
	default T id(String value) {
		getThis().setId(value);
		return getThis();
	}

	default T style(String value) {
		getThis().setStyle(value);
		return getThis();
	}

	default String text() {
		return getThis().getText();
	}

	default T text(String value) {
		getThis().setText(value);
		return getThis();
	}

	default Node graphic() {
		return getThis().getGraphic();
	}

	default T graphic(Node value) {
		getThis().setGraphic(value);
		return getThis();
	}

	default Node content() {
		return getThis().getContent();
	}

	default T content(Node value) {
		getThis().setContent(value);
		return getThis();
	}

	default ContextMenu contextMenu() {
		return getThis().getContextMenu();
	}

	default T contextMenu(ContextMenu value) {
		getThis().setContextMenu(value);
		return getThis();
	}

	default boolean closable() {
		return getThis().isClosable();
	}

	default T closable(boolean value) {
		getThis().setClosable(value);
		return getThis();
	}

	default EventHandler<Event> onSelectionChanged() {
		return getThis().getOnSelectionChanged();
	}

	default T onSelectionChanged(EventHandler<Event> value) {
		getThis().setOnSelectionChanged(value);
		return getThis();
	}

	default EventHandler<Event> onClosed() {
		return getThis().getOnClosed();
	}

	default T onClosed(EventHandler<Event> value) {
		getThis().setOnClosed(value);
		return getThis();
	}

	default Tooltip tooltip() {
		return getThis().getTooltip();
	}

	default T tooltip(Tooltip value) {
		getThis().setTooltip(value);
		return getThis();
	}

	default boolean disable() {
		return getThis().isDisable();
	}

	default boolean disabled() {
		return getThis().isDisabled();
	}

	default T disable(boolean value) {
		getThis().setDisable(value);
		return getThis();
	}

	default EventHandler<Event> onCloseRequest() {
		return getThis().getOnCloseRequest();
	}

	default T onCloseRequest(EventHandler<Event> value) {
		getThis().setOnCloseRequest(value);
		return getThis();
	}

	default Object userData() {
		return getThis().getUserData();
	}

	default T userData(Object value) {
		getThis().setUserData(value);
		return getThis();
	}

}
