package com.stox.fx.fluent.scene.layout;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public interface IFluentVBox<T extends VBox & IFluentVBox<T>> extends IFluentPane<T> {

	default T spacing(double value) {
		getThis().setSpacing(value);
		return getThis();
	}

	default T alignment(Pos value) {
		getThis().setAlignment(value);
		return getThis();
	}

	default T fillWidth(boolean value) {
		getThis().setFillWidth(value);
		return getThis();
	}

}
