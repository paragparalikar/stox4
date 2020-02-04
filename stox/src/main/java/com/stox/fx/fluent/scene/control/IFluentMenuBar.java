package com.stox.fx.fluent.scene.control;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public interface IFluentMenuBar<T extends MenuBar & IFluentMenuBar<T>> extends IFluentControl<T> {

	default T useSystemMenuBar(final boolean value) {
		getThis().setUseSystemMenuBar(value);
		return getThis();
	}
	
	default boolean useSystemMenuBar() {
		return getThis().isUseSystemMenuBar();
	}
	
	default ObservableList<Menu> menus(){
		return getThis().getMenus();
	}
	
}
