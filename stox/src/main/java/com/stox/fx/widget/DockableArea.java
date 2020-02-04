package com.stox.fx.widget;

import com.stox.fx.fluent.scene.layout.IFluentBorderPane;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public interface DockableArea<A extends BorderPane & IFluentBorderPane<A> & DockableArea<A>> extends MovableArea<A>, ResizableArea<A> {

	default A dockable(Node knob) {
		knob(knob).resizable(getThis()).managed(false).addHandler(MouseEvent.MOUSE_PRESSED, e -> getThis().toFront());
		return getThis();
	}
	
}
