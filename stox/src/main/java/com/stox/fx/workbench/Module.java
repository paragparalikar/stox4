package com.stox.fx.workbench;

import javafx.beans.value.ObservableValue;

public interface Module {

	String getId();
	
	String getIcon();
	
	ObservableValue<String> getName();
	
	ModuleView<?> buildModuleView();
	
}
