package com.stox.fx.workbench;

import com.stox.Context;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.control.IFluentMenuBar;
import com.stox.fx.widget.Icon;
import com.stox.module.data.donwloader.DownloaderModuleView;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import lombok.NonNull;

public class WorkbenchMenuBar extends MenuBar implements IFluentMenuBar<WorkbenchMenuBar>{

	private final Context context;
	private final Workbench workbench;
	
	public WorkbenchMenuBar(final Context context, final Workbench workbench) {
		this.context = context;
		this.workbench = workbench;
		getStyleClass().addAll("primary", "workbench-menu-bar");
		getMenus().add(buildNewMenu());
	}
	
	private void add(final ModuleView<?> moduleView) {
		workbench.add(moduleView);
		final Bounds bounds = workbench.visualBounds();
		moduleView.initDefaultBounds(bounds.getWidth(), bounds.getHeight());
	}
	
	private Menu buildNewMenu() {
		final Menu newMenu = new Menu();
		newMenu.textProperty().bind(context.getMessageSource().get("New"));
		newMenu.getItems().add(buildDownloaderMenuItem());
		return newMenu;
	}
	
	private ModuleMenuItem buildDownloaderMenuItem() {
		final ModuleMenuItem item = new ModuleMenuItem(Icon.DOWNLOAD, context.getMessageSource().get("Downloader"));
		item.setOnAction(event -> add(new DownloaderModuleView(context)));
		return item;
	}
	
	
	
	@Override
	public WorkbenchMenuBar getThis() {
		return this;
	}
	
}

class ModuleMenuItem extends MenuItem {

	public ModuleMenuItem(@NonNull final String icon, @NonNull final ObservableValue<String> textValue) {
		textProperty().bind(textValue);
		setGraphic(new FluentLabel(icon).classes("icon", "primary"));
		getStyleClass().add("primary");
	}

}
