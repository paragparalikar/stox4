package com.stox.fx.fluent.scene.layout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public interface IFluentPane<T extends Pane & IFluentPane<T>> extends IFluentRegion<T> {

	default T child(Node child) {
		getThis().getChildren().add(child);
		return getThis();
	}

	default T child(int index, Node child) {
		getThis().getChildren().add(index, child);
		return getThis();
	}
	
	default T firstChild(int index, Node child) {
		getThis().getChildren().add(0, child);
		return getThis();
	}
	
	default ObservableList<Node> children(){
		return getThis().getChildren();
	}

	default T children(Node... children) {
		getThis().getChildren().setAll(children);
		return getThis();
	}

}
