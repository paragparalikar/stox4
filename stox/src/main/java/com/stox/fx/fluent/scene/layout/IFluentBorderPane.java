package com.stox.fx.fluent.scene.layout;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public interface IFluentBorderPane<T extends BorderPane & IFluentBorderPane<T>> extends IFluentPane<T> {

	default Node center() {
		return getThis().getCenter();
	}

	default T center(Node value) {
		getThis().setCenter(value);
		return getThis();
	}

	default Node top() {
		return getThis().getTop();
	}

	default T top(Node value) {
		getThis().setTop(value);
		return getThis();
	}

	default Node bottom() {
		return getThis().getBottom();
	}

	default T bottom(Node value) {
		getThis().setBottom(value);
		return getThis();
	}

	default Node left() {
		return getThis().getLeft();
	}

	default T left(Node value) {
		getThis().setLeft(value);
		return getThis();
	}

	default Node right() {
		return getThis().getRight();
	}

	default T right(Node value) {
		getThis().setRight(value);
		return getThis();
	}

	
}
