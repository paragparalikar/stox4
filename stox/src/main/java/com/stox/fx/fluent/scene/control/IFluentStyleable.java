package com.stox.fx.fluent.scene.control;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;

public interface IFluentStyleable<T extends Styleable & IFluentStyleable<T>> {

	T getThis();

	default String typeSelector() {
		return getThis().getTypeSelector();
	}

	default String id() {
		return getThis().getId();
	}

	default ObservableList<String> styleClass() {
		return getThis().getStyleClass();
	}

	default T styleClass(String... values) {
		styleClass().addAll(values);
		return getThis();
	}

	default T clearStyleClass() {
		styleClass().clear();
		return getThis();
	}

	default T removeStyleClass(String... values) {
		styleClass().removeAll(values);
		return getThis();
	}

	default String style() {
		return getThis().getStyle();
	}

	default List<CssMetaData<? extends Styleable, ?>> cssMetaData() {
		return getThis().getCssMetaData();
	}

	default Styleable styleableParent() {
		return getThis().getStyleableParent();
	}

	default ObservableSet<PseudoClass> pseudoClassStates() {
		return getThis().getPseudoClassStates();
	}

}
