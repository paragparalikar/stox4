package com.stox.workbench.module;

import javafx.beans.value.ObservableValue;

public interface UiModule extends Module{

	String getId();
	
	String getIcon();
	
	ObservableValue<String> getName();
	
	ModuleView<?> buildModuleView();
	
}
