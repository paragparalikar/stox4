package com.stox.module.explorer;

import com.stox.Context;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.widget.Icon;
import com.stox.workbench.Workbench;
import com.stox.workbench.module.Module;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.MenuItem;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExplorerModule implements Module {

	@NonNull
	private final Context context;
	
	@Override
	public void start() {
		appendMenuItem(Icon.LIST, context.getMessageSource().get("Instrument Explorer"));
	}
	
	private void appendMenuItem(final String icon, final ObservableValue<String> name) {
		final MenuItem item = new MenuItem();
		item.textProperty().bind(name);
		item.getStyleClass().addAll("primary");
		item.setGraphic(new FluentLabel(icon).classes("primary","icon"));
		final Workbench workbench = context.getWorkbench();
		workbench.getTitleBar().getMenuBar().getNewMenu().getItems().add(item);
		item.setOnAction(event -> {
			final ExplorerView view = new ExplorerView(icon, name, context);
			final Bounds bounds = workbench.add(view).visualBounds();
			view.initDefaultBounds(bounds.getWidth(), bounds.getHeight());
		});
	}

	@Override
	public void stop() {
	}

}
