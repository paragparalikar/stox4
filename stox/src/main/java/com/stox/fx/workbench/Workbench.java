package com.stox.fx.workbench;

import java.util.Arrays;
import java.util.List;

import com.stox.Context;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.stage.IFluentStage;
import com.stox.fx.widget.MovableArea;
import com.stox.fx.widget.ResizableArea;
import com.stox.fx.widget.SnapPane;
import com.stox.fx.workbench.event.ModuleViewCloseRequestEvent;
import com.stox.module.data.donwloader.DownloaderModule;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public class Workbench extends Stage implements IFluentStage<Workbench>, MovableArea<Workbench>, ResizableArea<Workbench> {

	private final WorkbenchTitleBar titleBar;
	private final FluentBorderPane container;
	private final SnapPane snapPane = new SnapPane();
	@Getter(AccessLevel.PACKAGE)
	private final List<? extends Module> modules;

	public Workbench(@NonNull final Context context) {
		modules = Arrays.asList(new DownloaderModule(context));
		titleBar = new WorkbenchTitleBar(context, this);
		titleProperty().bind(context.getMessageSource().get("Stox"));
		container = new FluentBorderPane().top(titleBar).center(snapPane);
		final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		final Scene scene = new Scene(resizableWrapper().center(container), bounds.getWidth() / 2., bounds.getHeight() / 2);
		Application.setUserAgentStylesheet("stylex/base.css");
		scene.getStylesheets().addAll("stylex/label.css", "stylex/button.css", "stylex/menu.css", "stylex/scroll-bar.css", "stylex/table-view.css",
				"stylex/combo-box.css", "stylex/choice-box.css", "stylex/check-box.css", "stylex/text-field.css", "stylex/list-view.css", "stylex/progress-indicator.css",
				"stylex/progress-bar.css", "stylex/tool-bar.css", "stylex/titled-pane.css", "stylex/search-box.css", "stylex/override.css");
		scene.getStylesheets().addAll("stylex/workbench.css", "stylex/modal.css");
		style(StageStyle.UNDECORATED).knob(titleBar).maximized(Boolean.TRUE).scene(scene)
				.addEventHandler(ModuleViewCloseRequestEvent.TYPE, this::onModuleViewCloseRequested);
	}

	public Workbench add(@NonNull final ModuleView<?> moduleView) {
		snapPane.add(moduleView.getTitleBar(), moduleView);
		return this;
	}

	public Bounds visualBounds() {
		return snapPane.getBoundsInLocal();
	}
	
	private void onModuleViewCloseRequested(ModuleViewCloseRequestEvent event) {
		snapPane.getChildren().remove(event.getModuleView());
	}

	@Override
	public Workbench getThis() {
		return this;
	}

}
