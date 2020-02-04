package com.stox.fx.fluent.scene.control;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class FluentComboBox<T> extends ComboBox<T> implements IFluentComboBoxBase<T, FluentComboBox<T>> {

	public FluentComboBox() {
		super();
	}

	public FluentComboBox(ObservableList<T> items) {
		super(items);
	}

	@Override
	public FluentComboBox<T> getThis() {
		return this;
	}

	public ObservableList<T> items() {
		return getItems();
	}

	public FluentComboBox<T> items(ObservableList<T> items) {
		setItems(items);
		return this;
	}
	
	public FluentComboBox<T> items(List<T> items) {
		getItems().setAll(items);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public FluentComboBox<T> items(T...items) {
		getItems().setAll(items);
		return this;
	}

	public FluentComboBox<T> converter(StringConverter<T> converter) {
		setConverter(converter);
		return this;
	}

	public FluentComboBox<T> cellFactory(Callback<ListView<T>, ListCell<T>> value) {
		setCellFactory(value);
		return this;
	}

	public FluentComboBox<T> buttonCell(ListCell<T> value) {
		setButtonCell(value);
		return this;
	}

	public FluentComboBox<T> select(T value){
		getSelectionModel().select(value);
		return this;
	}
	
	public FluentComboBox<T> select(int index){
		getSelectionModel().select(index);
		return this;
	}
	
	public FluentComboBox<T> onSelect(ChangeListener<T> changeListener){
		getSelectionModel().selectedItemProperty().addListener(changeListener);
		return this;
	}
	
	public SingleSelectionModel<T> selectionModel(){
		return getSelectionModel();
	}
	
	public FluentComboBox<T> selectionModel(SingleSelectionModel<T> value) {
		setSelectionModel(value);
		return this;
	}

	public FluentComboBox<T> visibleRowCount(int value) {
		setVisibleRowCount(value);
		return this;
	}

	public FluentComboBox<T> placeholder(Node value) {
		setPlaceholder(value);
		return this;
	}

}
