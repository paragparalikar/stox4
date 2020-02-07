package com.stox.module.explorer;

import com.stox.Context;
import com.stox.fx.widget.Icon;
import com.stox.workbench.module.Module;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExplorerModule implements Module {

	@NonNull
	private final Context context;
	
	@Override
	public void start() {
		final String icon = Icon.LIST;
		final ObservableValue<String> textValue = context.getMessageSource().get("Instrument Explorer");
		context.getWorkbench().getTitleBar().getMenuBar().newMenuItem(icon, textValue, event -> {
			final ExplorerView view = new ExplorerView(icon, textValue, context);
			final Bounds bounds = context.getWorkbench().add(view).visualBounds();
			view.initDefaultBounds(bounds.getWidth(), bounds.getHeight());
		});
	}

	@Override
	public void stop() {
	}

}
