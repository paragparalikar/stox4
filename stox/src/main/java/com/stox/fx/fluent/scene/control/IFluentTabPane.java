package com.stox.fx.fluent.scene.control;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public interface IFluentTabPane<T extends TabPane & IFluentTabPane<T>> extends IFluentControl<T> {

	default ObservableList<Tab> tabs() {
		return getThis().getTabs();
	}

	default T tabs(Tab... values) {
		tabs().addAll(values);
		return getThis();
	}

	default T clearTabs() {
		tabs().clear();
		return getThis();
	}

	default T removeTabs(Tab... values) {
		tabs().removeAll(values);
		return getThis();
	}
	
	default T selectionModel(final SingleSelectionModel<Tab> model) {
		getThis().setSelectionModel(model);
		return getThis();
	}
	

	default SingleSelectionModel<Tab> selectionModel() {
		return getThis().getSelectionModel();
	}
	
	default T side(final Side side) {
		getThis().setSide(side);
		return getThis();
	}
	
	default Side side() {
		return getThis().getSide();
	}

	default T tabClosingPolicy(final TabClosingPolicy policy) {
		getThis().setTabClosingPolicy(policy);
		return getThis();
	}
	
	default TabClosingPolicy tabClosingPolicy() {
		return getThis().getTabClosingPolicy();
	}
	
	default T rotateGraphic(final boolean value) {
		getThis().setRotateGraphic(value);
		return getThis();
	}
	
	default boolean rotateGraphic() {
		return getThis().isRotateGraphic();
	}
	
	default T tabMinWidth(final double value) {
		getThis().setTabMinWidth(value);
		return getThis();
	}
	
	default T tabMaxWidth(final double value) {
		getThis().setTabMaxWidth(value);
		return getThis();
	}
	
	default T tabMinHeight(final double value) {
		getThis().setTabMinHeight(value);
		return getThis();
	}
	
	default T tabMaxHeight(final double value) {
		getThis().setTabMaxHeight(value);
		return getThis();
	}
	
	default double tabMinWidth() {
		return getThis().getTabMinWidth();
	}

	default double tabMaxWidth() {
		return getThis().getTabMaxWidth();
	}

	default double tabMinHeight() {
		return getThis().getTabMinHeight();
	}

	default double tabMaxHeight() {
		return getThis().getTabMaxHeight();
	}
}
