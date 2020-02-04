package com.stox.fx.fluent.scene;

import com.stox.fx.fluent.scene.control.IFluentStyleable;

import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public interface IFluentNode<T extends Node & IFluentNode<T>> extends IFluentStyleable<T>{

	default Parent parent() {
		return getThis().getParent();
	}
	
	default boolean disabled() {
		return getThis().isDisabled();
	}
	
	default boolean disable() {
		return getThis().isDisable();
	}
	
	default T disable(boolean value) {
		getThis().setDisable(value);
		return getThis();
	}
	
	default boolean managed() {
		return getThis().isManaged();
	}
	
	default T managed(boolean value){
		getThis().setManaged(value);
		return getThis();
	}
	
	default double layoutX() {
		return getThis().getLayoutX();
	}
	
	default T layoutX(double value){
		getThis().setLayoutX(value);
		return getThis();
	}
	
	default double layoutY() {
		return getThis().getLayoutY();
	}
	
	default T layoutY(double value){
		getThis().setLayoutY(value);
		return getThis();
	}

	default <V extends Event> T addHandler(final EventType<V> eventType, final EventHandler<? super V> eventHandler) {
		getThis().addEventHandler(eventType, eventHandler);
		return getThis();
	}
	
	default <V extends Event> T removeHandler(final EventType<V> eventType, final EventHandler<? super V> eventHandler) {
		getThis().removeEventHandler(eventType, eventHandler);
		return getThis();
	}

	default <V extends Event> T addFilter(final EventType<V> eventType, final EventHandler<? super V> eventFilter) {
		getThis().addEventFilter(eventType, eventFilter);
		return getThis();
	}

	default <V extends Event> T removeFilter(final EventType<V> eventType, final EventHandler<? super V> eventFilter) {
		getThis().removeEventFilter(eventType, eventFilter);
		return getThis();
	}
	
	default T pseudoClassState(PseudoClass pseudoClass, boolean active) {
		getThis().pseudoClassStateChanged(pseudoClass, active);
		return getThis();
	}
	
	default Object userData() {
		return getThis().getUserData();
	}
	
	default T userData(Object data) {
		getThis().setUserData(data);
		return getThis();
	}
	
	default boolean visible() {
		return getThis().isVisible();
	}

	default T visible(boolean value) {
		getThis().setVisible(value);
		return getThis();
	}
	
	default Cursor cursor() {
		return getThis().getCursor();
	}

	default T cursor(Cursor cursor) {
		getThis().setCursor(cursor);
		return getThis();
	}
	
	default double opacity() {
		return getThis().getOpacity();
	}

	default T opacity(double value) {
		getThis().setOpacity(value);
		return getThis();
	}
	
	default String style() {
		return getThis().getStyle();
	}

	default T style(String style) {
		getThis().setStyle(style);
		return getThis();
	}

	default ObservableList<String> classes(){
		return getThis().getStyleClass();
	}
	
	default T classes(String... classes) {
		getThis().getStyleClass().addAll(classes);
		return getThis();
	}
	
	default T classes(boolean flag, String...classes){
		if(flag){
			getThis().getStyleClass().addAll(classes);
		}else{
			getThis().getStyleClass().removeAll(classes);
		}
		return getThis();
	}

	default T fullHeight() {
		VBox.setVgrow(getThis(), Priority.ALWAYS);
		return getThis();
	}

	default T fullWidth() {
		HBox.setHgrow(getThis(), Priority.ALWAYS);
		return getThis();
	}

	default T fullArea() {
		fullHeight();
		return fullWidth();
	}
	
	default boolean focused() {
		return getThis().isFocused();
	}
	
	default boolean focusTraversable() {
		return getThis().isFocusTraversable();
	}
		
	default T focusTraversable(boolean value){
		getThis().setFocusTraversable(value);
		return getThis();
	}

}
