package com.stox.fx.fluent.scene.control;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class FluentTableColumn<S, T> extends TableColumn<S, T>
		implements IFluentTableColumnBase<S, T, FluentTableColumn<S, T>> {

	public FluentTableColumn() {
		super();
	}

	public FluentTableColumn(String text) {
		super(text);
	}
	
	@Override
	public FluentTableColumn<S, T> getThis() {
		return this;
	}

	public FluentTableColumn<S, T> cellFactory(Callback<TableColumn<S, T>, TableCell<S, T>> value) {
		setCellFactory(value);
		return this;
	}

	public FluentTableColumn<S, T> cellValueFactory(Callback<CellDataFeatures<S, T>, ObservableValue<T>> value) {
		setCellValueFactory(value);
		return this;
	}

	public FluentTableColumn<S, T> sortType(SortType value) {
		setSortType(value);
		return this;
	}

	public FluentTableColumn<S, T> onEditStart(EventHandler<CellEditEvent<S, T>> value) {
		setOnEditStart(value);
		return this;
	}

	public FluentTableColumn<S, T> onEditCommit(EventHandler<CellEditEvent<S, T>> value) {
		setOnEditCommit(value);
		return this;
	}

	public FluentTableColumn<S, T> onEditCancel(EventHandler<CellEditEvent<S, T>> value) {
		setOnEditCancel(value);
		return this;
	}

}
