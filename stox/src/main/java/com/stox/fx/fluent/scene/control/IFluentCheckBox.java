package com.stox.fx.fluent.scene.control;

import javafx.scene.control.CheckBox;

public interface IFluentCheckBox<T extends CheckBox & IFluentCheckBox<T>> extends IFluentButtonBase<T> {

	public default T allowIndeterminate(boolean value){
		getThis().setAllowIndeterminate(value);
		return getThis();
	}
	
	public default T indeterminate(boolean value){
		getThis().setIndeterminate(value);
		return getThis();
	}
	
	public default T selected(boolean value){
		getThis().setSelected(value);
		return getThis();
	}
	
}
