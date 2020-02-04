package com.stox.fx.fluent.scene.control;

import javafx.scene.control.ProgressIndicator;

public interface IFluentProgressIndicator<T extends ProgressIndicator & IFluentProgressIndicator<T>>
		extends IFluentControl<T> {

	default T progress(double value) {
		getThis().setProgress(value);
		return getThis();
	}

	default double progress() {
		return getThis().getProgress();
	}

}
