package com.stox.workbench;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.stage.FluentStage;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.SnapPane;
import com.stox.fx.widget.handler.MovableMouseEventHandler;
import com.stox.fx.widget.handler.ResizeMouseEventHandler;
import com.stox.workbench.link.Link;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.ModuleViewState;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.NonNull;

public class Workbench {

	@Getter
	private final WorkbenchTitleBar titleBar;

	@Getter
	private final WorkbenchInfoBar infoBar;

	private final Stage stage;
	private final FluentBorderPane root;
	private final SnapPane snapPane = new SnapPane();

	public Workbench(@NonNull final FxMessageSource messageSource, @NonNull final FluentStage stage) {
		this.stage = stage;
		this.infoBar = new WorkbenchInfoBar(messageSource);
		this.titleBar = new WorkbenchTitleBar(messageSource);
		this.root = ResizeMouseEventHandler.resizable(new FluentBorderPane(), stage);
		MovableMouseEventHandler.movable(titleBar.getNode(), stage);
		stage.style(StageStyle.UNDECORATED)
				.scene(buildScene())
				.titleProperty().bind(messageSource.get("product.name", "Stox"));
	}

	public WorkbenchState state() {
		final Map<String,LinkState> linkStates = new HashMap<>();
		Arrays.stream(Link.values()).forEach(link -> linkStates.put(link.getColor().toString(), link.getState()));
		return new WorkbenchState()
				.x(stage.getX())
				.y(stage.getY())
				.width(stage.getWidth())
				.height(stage.getHeight())
				.maximized(titleBar.maximized())
				.linkStates(linkStates);
	}

	public Workbench state(final WorkbenchState state) {
		Optional.ofNullable(state).ifPresent(titleBar::state);
		return this;
	}

	private Scene buildScene() {
		final Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		final FluentBorderPane container = new FluentBorderPane().top(titleBar.getNode()).center(snapPane).bottom(infoBar.getNode());
		final Scene scene = new Scene(root.center(container), bounds.getWidth(), bounds.getHeight());
		Application.setUserAgentStylesheet("stylex/base.css");
		scene.getStylesheets().addAll("stylex/label.css", "stylex/button.css", "stylex/menu.css", "stylex/scroll-bar.css", "stylex/table-view.css",
				"stylex/combo-box.css", "stylex/choice-box.css", "stylex/check-box.css", "stylex/text-field.css", "stylex/list-view.css", "stylex/progress-indicator.css",
				"stylex/progress-bar.css", "stylex/tool-bar.css", "stylex/titled-pane.css", "stylex/search-box.css", "stylex/link.css", "stylex/chart.css", "stylex/override.css");
		scene.getStylesheets().addAll("stylex/workbench.css", "stylex/modal.css");
		return scene;
	}

	public <T extends ModuleViewState> ModuleView<T> add(@NonNull final ModuleView<T> moduleView, final T state) {
		snapPane.add(moduleView.getTitleBar().getNode(), moduleView.getNode());
		moduleView.start(state, visualBounds());
		snapPane.snap(moduleView.getNode());
		return moduleView;
	}

	public <T extends ModuleViewState> ModuleView<T> remove(@NonNull final ModuleView<T> moduleView) {
		moduleView.stop(visualBounds());
		snapPane.getChildren().remove(moduleView.getNode());
		return moduleView;
	}

	public Bounds visualBounds() {
		return snapPane.getBoundsInLocal();
	}
}
