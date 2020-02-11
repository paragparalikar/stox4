package com.stox.workbench;

import java.util.concurrent.atomic.AtomicReference;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.stage.FluentStage;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.SnapPane;
import com.stox.fx.widget.handler.MovableMouseEventHandler;
import com.stox.fx.widget.handler.ResizeMouseEventHandler;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.ModuleViewState;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.NonNull;

public class Workbench {

	@Getter
	private final WorkbenchTitleBar titleBar;
	
	@Getter
	private final WorkbenchInfoBar infoBar;
	
	private final SnapPane snapPane = new SnapPane();
	private final FluentStage stage = new FluentStage();
	private final FluentBorderPane root = ResizeMouseEventHandler.resizable(new FluentBorderPane(), stage);

	public Workbench(@NonNull final FxMessageSource messageSource, final AtomicReference<FluentStage> stageReference) {
		stageReference.set(stage);
		this.infoBar = new WorkbenchInfoBar(messageSource);
		this.titleBar = new WorkbenchTitleBar(messageSource);
		MovableMouseEventHandler.movable(titleBar.getNode(), stage);
		stage.style(StageStyle.UNDECORATED)
			.maximized(Boolean.TRUE)
			.scene(buildScene())
			.titleProperty().bind(messageSource.get("product.name","Stox"));
	}
	
	private Scene buildScene() {
		final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		final FluentBorderPane container = new FluentBorderPane().top(titleBar.getNode()).center(snapPane).bottom(infoBar.getNode());
		final Scene scene = new Scene(root.center(container), bounds.getWidth(), bounds.getHeight());
		Application.setUserAgentStylesheet("stylex/base.css");
		scene.getStylesheets().addAll("stylex/label.css", "stylex/button.css", "stylex/menu.css", "stylex/scroll-bar.css", "stylex/table-view.css",
				"stylex/combo-box.css", "stylex/choice-box.css", "stylex/check-box.css", "stylex/text-field.css", "stylex/list-view.css", "stylex/progress-indicator.css",
				"stylex/progress-bar.css", "stylex/tool-bar.css", "stylex/titled-pane.css", "stylex/search-box.css", "stylex/link.css", "stylex/override.css");
		scene.getStylesheets().addAll("stylex/workbench.css", "stylex/modal.css");
		return scene;
	}

	public <T extends ModuleViewState> ModuleView<T> add(@NonNull final ModuleView<T> moduleView) {
		snapPane.add(moduleView.getTitleBar().getNode(), moduleView.getNode());
		return moduleView;
	}
	
	public <T extends ModuleViewState> ModuleView<T> remove(@NonNull final ModuleView<T> moduleView) {
		snapPane.getChildren().remove(moduleView.getNode());
		return moduleView;
	}
				
	public Bounds visualBounds() {
		return snapPane.getBoundsInLocal();
	}
}
