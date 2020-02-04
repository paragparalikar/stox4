package com.stox.fx.fluent.scene.control;

import java.util.Comparator;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumnBase;

public interface IFluentTableColumnBase<S, T, V extends TableColumnBase<S, T> & IFluentTableColumnBase<S, T, V>> {

	V getThis();

	default V text(String value) {
		getThis().setText(value);
		return getThis();
	}

	default V visible(boolean value) {
		getThis().setVisible(value);
		return getThis();
	}

	default V contextMenu(ContextMenu value) {
		getThis().setContextMenu(value);
		return getThis();
	}

	default V id(String value) {
		getThis().setId(value);
		return getThis();
	}

	default V style(String value) {
		getThis().setStyle(value);
		return getThis();
	}

	default V graphic(Node value) {
		getThis().setGraphic(value);
		return getThis();
	}

	default V sortNode(Node value) {
		getThis().setSortNode(value);
		return getThis();
	}

	default V prefWidth(double value) {
		getThis().setPrefWidth(value);
		return getThis();
	}

	default V minWidth(double value) {
		getThis().setMinWidth(value);
		return getThis();
	}

	default V maxWidth(double value) {
		getThis().setMaxWidth(value);
		return getThis();
	}

	default V resizable(boolean value) {
		getThis().setResizable(value);
		return getThis();
	}

	default V sortable(boolean value) {
		getThis().setSortable(value);
		return getThis();
	}

	default V editable(boolean value) {
		getThis().setEditable(value);
		return getThis();
	}

	default V comparator(Comparator<T> value) {
		getThis().setComparator(value);
		return getThis();
	}

	default V userDate(Object value) {
		getThis().setUserData(value);
		return getThis();
	}
}
