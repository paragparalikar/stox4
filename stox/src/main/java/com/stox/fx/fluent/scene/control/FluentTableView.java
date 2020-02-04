package com.stox.fx.fluent.scene.control;

import java.util.Arrays;
import java.util.Collection;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class FluentTableView<S> extends TableView<S> implements IFluentControl<FluentTableView<S>> {

	public FluentTableView() {
	}

	public FluentTableView(ObservableList<S> items) {
		super(items);
	}

	@SafeVarargs
	public FluentTableView(TableColumn<S, ?>... columns) {
		columns().addAll(Arrays.asList(columns));
	}
	
	@Override
	public FluentTableView<S> getThis() {
		return this;
	}

	public ObservableList<TableColumn<S, ?>> columns() {
		return getColumns();
	}

	@SafeVarargs
	public final FluentTableView<S> columns(TableColumn<S, ?>... columns) {
		columns().addAll(Arrays.asList(columns));
		return this;
	}

	public ObservableList<S> items() {
		return getItems();
	}

	public FluentTableView<S> items(ObservableList<S> value) {
		setItems(value);
		return this;
	}

	public FluentTableView<S> items(Collection<S> value) {
		getItems().setAll(value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public FluentTableView<S> items(S... value) {
		getItems().setAll(value);
		return this;
	}

	public boolean tableMenuButtonVisible() {
		return isTableMenuButtonVisible();
	}

	public FluentTableView<S> tableMenuButtonVisible(boolean value) {
		setTableMenuButtonVisible(value);
		return this;
	}

	@SuppressWarnings("rawtypes")
	public Callback<ResizeFeatures, Boolean> columnResizePolicy() {
		return getColumnResizePolicy();
	}

	@SuppressWarnings("rawtypes")
	public FluentTableView<S> columnResizePolicy(Callback<ResizeFeatures, Boolean> callback) {
		setColumnResizePolicy(callback);
		return this;
	}

	public Callback<TableView<S>, TableRow<S>> rowFactory() {
		return getRowFactory();
	}

	public FluentTableView<S> rowFactory(Callback<TableView<S>, TableRow<S>> value) {
		setRowFactory(value);
		return this;
	}

	public Node placeholder() {
		return getPlaceholder();
	}

	public FluentTableView<S> placeholder(Node node) {
		setPlaceholder(node);
		return this;
	}

	public TableViewSelectionModel<S> selectionModel() {
		return getSelectionModel();
	}

	public FluentTableView<S> selectionModel(TableViewSelectionModel<S> value) {
		setSelectionModel(value);
		return this;
	}

	public FluentTableView<S> select(S item) {
		selectionModel().select(item);
		return this;
	}

	public FluentTableView<S> select(int row) {
		selectionModel().select(row);
		return this;
	}

	public FluentTableView<S> clearSelection() {
		selectionModel().clearSelection();
		return this;
	}

	public S selectedItem() {
		return selectionModel().getSelectedItem();
	}

	public ObservableList<S> selectedItems() {
		return selectionModel().getSelectedItems();
	}

	public TableViewFocusModel<S> focusModel() {
		return getFocusModel();
	}

	public FluentTableView<S> focusModel(TableViewFocusModel<S> value) {
		setFocusModel(value);
		return this;
	}

	public boolean editable() {
		return isEditable();
	}

	public FluentTableView<S> editable(boolean value) {
		setEditable(value);
		return this;
	}

	public double fixedCellSize() {
		return getFixedCellSize();
	}

	public FluentTableView<S> fixedCellSize(double value) {
		setFixedCellSize(value);
		return this;
	}

	public Callback<TableView<S>, Boolean> sortPolicy() {
		return getSortPolicy();
	}

	public FluentTableView<S> sortPolicy(Callback<TableView<S>, Boolean> callback) {
		setSortPolicy(callback);
		return this;
	}

	public FluentTableView<S> onSort(EventHandler<SortEvent<TableView<S>>> value) {
		setOnSort(value);
		return this;
	}

	public FluentTableView<S> onScrollTo(EventHandler<ScrollToEvent<Integer>> value) {
		setOnScrollTo(value);
		return this;
	}

	public FluentTableView<S> onScrollToColumn(EventHandler<ScrollToEvent<TableColumn<S, ?>>> value) {
		setOnScrollToColumn(value);
		return this;
	}
	
	public FluentTableView<S> onItemSelected(ChangeListener<S> listener){
		getSelectionModel().selectedItemProperty().addListener(listener);
		return this;
	}

}
