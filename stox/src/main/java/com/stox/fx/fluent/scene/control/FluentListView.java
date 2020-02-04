package com.stox.fx.fluent.scene.control;

import java.util.Collection;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.FocusModel;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

public class FluentListView<T> extends ListView<T> implements IFluentControl<FluentListView<T>> {

	public FluentListView() {
	}

	public FluentListView(ObservableList<T> items) {
		super(items);
	}

	@Override
	public FluentListView<T> getThis() {
		return this;
	}

	public ObservableList<T> items() {
		return getItems();
	}

	public FluentListView<T> items(ObservableList<T> value) {
		setItems(value);
		return this;
	}

	public FluentListView<T> items(Collection<T> values) {
		getItems().addAll(values);
		return this;
	}

	@SuppressWarnings("unchecked")
	public FluentListView<T> items(T... values) {
		getItems().addAll(values);
		return this;
	}

	public Node placeholder() {
		return getPlaceholder();
	}

	public FluentListView<T> placeholder(Node node) {
		setPlaceholder(node);
		return this;
	}

	public MultipleSelectionModel<T> selectionModel() {
		return getSelectionModel();
	}

	public FluentListView<T> selectionModel(MultipleSelectionModel<T> value) {
		setSelectionModel(value);
		return this;
	}

	public FluentListView<T> select(T value) {
		selectionModel().select(value);
		return this;
	}

	public FluentListView<T> multipleSelectionMode() {
		selectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		return this;
	}

	public FluentListView<T> singleSelectionMode() {
		selectionModel().setSelectionMode(SelectionMode.SINGLE);
		return this;
	}

	public FocusModel<T> focusModel() {
		return getFocusModel();
	}

	public FluentListView<T> focusModel(FocusModel<T> value) {
		setFocusModel(value);
		return this;
	}

	public Orientation orientation() {
		return getOrientation();
	}

	public FluentListView<T> orientation(Orientation value) {
		setOrientation(value);
		return this;
	}

	public Callback<ListView<T>, ListCell<T>> cellFactory() {
		return getCellFactory();
	}

	public FluentListView<T> cellFactory(Callback<ListView<T>, ListCell<T>> value) {
		setCellFactory(value);
		return this;
	}

	public double fixedCellSize() {
		return getFixedCellSize();
	}

	public FluentListView<T> fixedCellSize(double value) {
		setFixedCellSize(value);
		return this;
	}

	public boolean editable() {
		return isEditable();
	}

	public FluentListView<T> editable(boolean value) {
		setEditable(value);
		return this;
	}

	public FluentListView<T> onEditStart(EventHandler<ListView.EditEvent<T>> value) {
		setOnEditStart(value);
		return this;
	}

	public FluentListView<T> onEditCommit(EventHandler<ListView.EditEvent<T>> value) {
		setOnEditCommit(value);
		return this;
	}

	public FluentListView<T> onEditCancel(EventHandler<ListView.EditEvent<T>> value) {
		setOnEditCancel(value);
		return this;
	}

	public FluentListView<T> onScrollTo(EventHandler<ScrollToEvent<Integer>> value) {
		setOnScrollTo(value);
		return this;
	}
}
