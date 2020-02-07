package com.stox.workbench.module;

import com.stox.Context;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UiModule implements Module {

	@NonNull
	@Getter(AccessLevel.PROTECTED)
	private final Context context;
	
	protected abstract String getIcon();
	
	protected abstract ObservableValue<String> getModuleName();
	
	protected abstract ModuleView buildModuleView();
	
	@Override
	public void start() {
		context.getWorkbench().getTitleBar().getMenuBar().newMenuItem(getIcon(), getModuleName(), event -> {
			final ModuleView view = buildModuleView();
			final Bounds bounds = context.getWorkbench().add(view).visualBounds();
			view.initDefaultBounds(bounds.getWidth(), bounds.getHeight());
		});
	}

	@Override
	public void stop() {
		
	}

}
