package com.stox.fx.fluent.scene.layout;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public interface IFluentHBox<T extends HBox & IFluentHBox<T>> extends IFluentPane<T> {

	default T spacing(double value) {
		getThis().setSpacing(value);
		return getThis();
	}

	default T alignment(Pos value) {
		getThis().setAlignment(value);
		return getThis();
	}

	default T fillHeight(boolean value) {
		getThis().setFillHeight(value);
		return getThis();
	}

}
