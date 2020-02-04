package com.stox.fx.fluent.scene.control;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;

public class FluentTab extends Tab implements IFluentStyleable<FluentTab> {

	public FluentTab() {
	}

	public FluentTab(String text) {
		super(text);
	}

	public FluentTab(String text, Node content) {
		super(text, content);
	}

	@Override
	public FluentTab getThis() {
		return this;
	}

	public FluentTab id(String value) {
		setId(value);
		return this;
	}

	public FluentTab style(String value) {
		setStyle(value);
		return this;
	}

	public String text() {
		return getText();
	}

	public FluentTab text(String value) {
		setText(value);
		return this;
	}

	public Node graphic() {
		return getGraphic();
	}

	public FluentTab graphic(Node value) {
		setGraphic(value);
		return this;
	}

	public Node content() {
		return getContent();
	}

	public FluentTab content(Node value) {
		setContent(value);
		return this;
	}

	public ContextMenu contextMenu() {
		return getContextMenu();
	}

	public FluentTab contextMenu(ContextMenu value) {
		setContextMenu(value);
		return this;
	}

	public boolean closable() {
		return isClosable();
	}

	public FluentTab closable(boolean value) {
		setClosable(value);
		return this;
	}

	public EventHandler<Event> onSelectionChanged() {
		return getOnSelectionChanged();
	}

	public FluentTab onSelectionChanged(EventHandler<Event> value) {
		setOnSelectionChanged(value);
		return this;
	}

	public EventHandler<Event> onClosed() {
		return getOnClosed();
	}

	public FluentTab onClosed(EventHandler<Event> value) {
		setOnClosed(value);
		return this;
	}

	public Tooltip tooltip() {
		return getTooltip();
	}

	public FluentTab tooltip(Tooltip value) {
		setTooltip(value);
		return this;
	}

	public boolean disable() {
		return isDisable();
	}

	public boolean disabled() {
		return isDisabled();
	}

	public FluentTab disable(boolean value) {
		setDisable(value);
		return this;
	}

	public EventHandler<Event> onCloseRequest() {
		return getOnCloseRequest();
	}

	public FluentTab onCloseRequest(EventHandler<Event> value) {
		setOnCloseRequest(value);
		return this;
	}

	public Object userData() {
		return getUserData();
	}

	public FluentTab userData(Object value) {
		setUserData(value);
		return this;
	}

}
