package com.stox.fx.fluent.scene.control;

import com.stox.fx.fluent.scene.layout.IFluentRegion;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

public interface IFluentControl<T extends Control & IFluentControl<T>> extends IFluentRegion<T> {

	default T tooltip(Tooltip value) {
		getThis().setTooltip(value);
		return getThis();
	}
	
	default Tooltip tooltip() {
		return getThis().getTooltip();
	}

	default T contextMenu(ContextMenu value) {
		getThis().setContextMenu(value);
		return getThis();
	}

}
