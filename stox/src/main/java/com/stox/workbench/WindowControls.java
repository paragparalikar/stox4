package com.stox.workbench;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.layout.IFluentHBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.FxMessageSource;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
public class WindowControls extends HBox implements IFluentHBox<WindowControls> {

	private final FluentButton minimizeButton = new FluentButton(Icon.WINDOW_MINIMIZE).classes("primary", "icon", "first").tooltip(new Tooltip());
	private final FluentButton restoreButton = new FluentButton(Icon.WINDOW_RESTORE).classes("primary", "icon", "middle").tooltip(new Tooltip());
	private final FluentButton exitButton = new FluentButton(Icon.TIMES).classes("primary", "icon", "last", "hover-danger").tooltip(new Tooltip());

	public WindowControls(final FxMessageSource language) {
		minimizeButton.onAction(this::minimize).tooltip().textProperty().bind(language.get("Minimize"));
		restoreButton.onAction(this::toggleMaximizeRestore).tooltip().textProperty().bind(language.get("Maximize"));
		exitButton.onAction(this::exit).tooltip().textProperty().bind(language.get("Exit"));
		children(minimizeButton, restoreButton, exitButton);
	}
	
	private void minimize(ActionEvent event) {
		Stage.class.cast(getScene().getWindow()).setIconified(Boolean.TRUE);
	}
	
	private void toggleMaximizeRestore(ActionEvent event) {
		final Stage stage = Stage.class.cast(getScene().getWindow());
		stage.setMaximized(!stage.isMaximized());
	}
	
	private void exit(ActionEvent event) {
		Platform.exit();
	}

	@Override
	public WindowControls getThis() {
		return this;
	}

}
