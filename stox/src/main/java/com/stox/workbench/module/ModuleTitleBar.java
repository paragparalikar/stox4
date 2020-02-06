package com.stox.workbench.module;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Spacer;
import com.stox.fx.widget.TitleBar;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import lombok.NonNull;

public class ModuleTitleBar extends TitleBar {

	public ModuleTitleBar(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final EventHandler<ActionEvent> closeEventHandler) {
		final FluentLabel graphic = new FluentLabel(icon).classes("primary", "icon");
		final FluentLabel titleLabel = new FluentLabel().classes("primary", "title-text").fullHeight();
		titleLabel.textProperty().bind(titleValue);
		final FluentButton closeButton = new FluentButton(Icon.TIMES).classes("primary", "icon", "hover-danger").onAction(closeEventHandler);
		append(Side.RIGHT, closeButton).center(new FluentHBox(graphic, titleLabel, new Spacer()));
	}

}
