package com.stox.workbench;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class WindowControls implements HasNode<Node>{

	@Getter(AccessLevel.PACKAGE)
	private boolean maximized;
	private double x, y, width, height;
	private final FluentButton minimizeButton = new FluentButton(Icon.WINDOW_MINIMIZE).classes("primary", "icon", "first");
	private final FluentButton restoreButton = new FluentButton(Icon.WINDOW_MAXIMIZE).classes("primary", "icon", "middle");
	private final FluentButton exitButton = new FluentButton(Icon.TIMES).classes("primary", "icon", "last", "hover-danger");
	private final HBox container = new HBox(minimizeButton, restoreButton, exitButton);

	public WindowControls(final FxMessageSource language) {
		minimizeButton.onAction(this::minimize).tooltip(Ui.tooltip(language.get("Minimize")));
		restoreButton.onAction(this::toggleMaximizeRestore).tooltip(Ui.tooltip(language.get("Maximize")));
		exitButton.onAction(this::exit).tooltip(Ui.tooltip(language.get("Exit")));
	}
	
	WindowControls state(@NonNull final WorkbenchState state) {
		x = state.x();
		y = state.y();
		width = state.width();
		height = state.height();
		maximized = state.maximized();
		toggleMaximizeRestore(null);
		maximized = state.maximized();
		return this;
	}
	
	private void minimize(ActionEvent event) {
		Stage.class.cast(window()).setIconified(Boolean.TRUE);
	}
	
	WindowControls toggleMaximizeRestore(ActionEvent event) {
		final Window window = window();
		if(maximized) {
			restore(window);
			restoreButton.text(Icon.WINDOW_MAXIMIZE);
			maximized = false;
		} else {
			maximize(cache(window()));
			restoreButton.text(Icon.WINDOW_RESTORE);
			maximized = true;
		}
		return this;
	}
	
	private void exit(ActionEvent event) {
		Platform.exit();
	}
	
	private Window window() {
		return container.getScene().getWindow();
	}
	
	private Window cache(Window window) {
		x = window.getX();
		y = window.getY();
		width = window.getWidth();
		height = window.getHeight();
		return window;
	}
		
	private void maximize(final Window window) {
		final Rectangle2D rectangle = screen().getVisualBounds();
		window.setX(rectangle.getMinX());
		window.setY(rectangle.getMinY());
		window.setWidth(rectangle.getWidth());
		window.setHeight(rectangle.getHeight());
	}
	
	private Screen screen() {
		final Point2D point = new Point2D(x, y);
		return Screen.getScreens().stream()
				.filter(s -> s.getBounds().contains(point))
				.findFirst().orElse(Screen.getPrimary());
	}
	
	private void restore(final Window window) {
		window.setX(x);
		window.setY(y);
		window.setWidth(width);
		window.setHeight(height);
	}

	@Override
	public Node getNode() {
		return container;
	}

}
