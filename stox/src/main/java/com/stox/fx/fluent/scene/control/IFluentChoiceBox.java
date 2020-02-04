package com.stox.fx.fluent.scene.control;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.StringConverter;

public interface IFluentChoiceBox<T, V extends ChoiceBox<T> & IFluentChoiceBox<T, V>> extends IFluentControl<V> {

	public default V selectionModel(SingleSelectionModel<T> value){
		getThis().setSelectionModel(value);
		return getThis();
	}
	
	public default V items(ObservableList<T> value){
		getThis().setItems(value);
		return getThis();
	}
	
	public default V converter(StringConverter<T> value){
		getThis().setConverter(value);
		return getThis();
	}
	
	public default V value(T value){
		getThis().setValue(value);
		return getThis();
	}
	
	public default V onAction(EventHandler<ActionEvent> value){
		getThis().setOnAction(value);
		return getThis();
	}
	
	public default V onShowing(EventHandler<Event> value){
		getThis().setOnShowing(value);
		return getThis();
	}
	
	public default V onShown(EventHandler<Event> value){
		getThis().setOnShown(value);
		return getThis();
	}
	
	public default V onHiding(EventHandler<Event> value){
		getThis().setOnHiding(value);
		return getThis();
	}
	
	public default V onHidden(EventHandler<Event> value){
		getThis().setOnHidden(value);
		return getThis();
	}
	
}
