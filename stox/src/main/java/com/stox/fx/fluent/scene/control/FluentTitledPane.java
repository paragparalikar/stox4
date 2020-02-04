package com.stox.fx.fluent.scene.control;

import java.util.Objects;
import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;

public class FluentTitledPane extends TitledPane implements IFluentControl<FluentTitledPane> {

	private final ChangeListener<Boolean> listener = (o, old, value) -> {
		if (value) {
			onExpanded();
		} else {
			onCollapsed();
		}
	};

	public FluentTitledPane() {
		expandedProperty().addListener(listener);
	}

	public FluentTitledPane(String title, Node content) {
		super(title, content);
		expandedProperty().addListener(listener);
	}

	@Override
	public FluentTitledPane getThis() {
		return this;
	}

	public FluentTitledPane content(Node value) {
		setContent(value);
		return this;
	}

	public Node content() {
		return getContent();
	}

	public FluentTitledPane expanded(boolean value) {
		setExpanded(value);
		return this;
	}

	public boolean expanded() {
		return isExpanded();
	}

	protected void onExpanded() {

	}

	protected void onCollapsed() {

	}

	public FluentTitledPane onExpanded(Consumer<FluentTitledPane> consumer) {
		Objects.requireNonNull(consumer, "callback can not be null");
		expandedProperty().addListener((o, old, value) -> {
			if (value) {
				consumer.accept(this);
			}
		});
		return this;
	}

	public FluentTitledPane onCollapsed(Consumer<FluentTitledPane> consumer) {
		Objects.requireNonNull(consumer, "callback can not be null");
		expandedProperty().addListener((o, old, value) -> {
			if (!value) {
				consumer.accept(this);
			}
		});
		return this;
	}

	public FluentTitledPane animated(boolean value) {
		setAnimated(value);
		return this;
	}

	public boolean animated() {
		return isAnimated();
	}

	public FluentTitledPane collapsible(boolean value) {
		setCollapsible(value);
		return this;
	}

	public boolean collapsible() {
		return isCollapsible();
	}

}
