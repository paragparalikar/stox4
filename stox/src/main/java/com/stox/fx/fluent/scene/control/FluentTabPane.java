package com.stox.fx.fluent.scene.control;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class FluentTabPane extends TabPane implements IFluentControl<FluentTabPane> {

	public FluentTabPane() {
	}

	public FluentTabPane(Tab... tabs) {
		super(tabs);
	}

	@Override
	public FluentTabPane getThis() {
		return this;
	}

	public ObservableList<Tab> tabs() {
		return getTabs();
	}

	public FluentTabPane tabs(Tab... values) {
		tabs().addAll(values);
		return this;
	}

	public FluentTabPane clearTabs() {
		tabs().clear();
		return this;
	}

	public FluentTabPane removeTabs(Tab... values) {
		tabs().removeAll(values);
		return this;
	}

	public SingleSelectionModel<Tab> selectionModel() {
		return getSelectionModel();
	}

	public FluentTabPane selectionModel(SingleSelectionModel<Tab> value) {
		setSelectionModel(value);
		return this;
	}

	public Side side() {
		return getSide();
	}

	public FluentTabPane side(Side value) {
		setSide(value);
		return this;
	}

	public TabClosingPolicy tabClosingPolicy() {
		return getTabClosingPolicy();
	}

	public FluentTabPane tabClosingPolicy(TabClosingPolicy value) {
		setTabClosingPolicy(value);
		return this;
	}

	public boolean rotateGraphic() {
		return isRotateGraphic();
	}

	public FluentTabPane rotateGraphic(boolean value) {
		setRotateGraphic(value);
		return this;
	}

	public double tabMinWidth() {
		return getTabMinWidth();
	}

	public FluentTabPane tabMinWidth(double value) {
		setTabMinWidth(value);
		return this;
	}

	public double tabMaxWidth() {
		return getTabMaxWidth();
	}

	public FluentTabPane tabMaxWidth(double value) {
		setTabMaxWidth(value);
		return this;
	}

	public double tabMinHeight() {
		return getTabMinHeight();
	}

	public FluentTabPane tabMinHeight(double value) {
		setTabMinHeight(value);
		return this;
	}

	public double tabMaxHeight() {
		return getTabMaxHeight();
	}

	public FluentTabPane tabMaxHeight(double value) {
		setTabMaxHeight(value);
		return this;
	}
}
