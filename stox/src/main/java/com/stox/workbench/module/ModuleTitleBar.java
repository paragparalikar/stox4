package com.stox.workbench.module;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.control.FluentToggleButton;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Spacer;
import com.stox.fx.widget.TitleBar;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public class ModuleTitleBar implements HasNode<Node> {
	
	@Getter(AccessLevel.PROTECTED)
	private final TitleBar titleBar = new TitleBar();

	public ModuleTitleBar(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final EventHandler<ActionEvent> closeEventHandler) {
		final FluentLabel graphic = new FluentLabel(icon).classes("primary", "icon");
		final FluentLabel titleLabel = new FluentLabel().classes("primary", "title-text").fullHeight();
		titleLabel.textProperty().bind(titleValue);
		final FluentButton closeButton = new FluentButton(Icon.TIMES).classes("primary", "icon", "hover-danger").onAction(closeEventHandler);
		titleBar.append(Side.RIGHT, closeButton).center(new FluentHBox(graphic, titleLabel, new Spacer()));
	}
	
	protected ModuleTitleBar appendToggleNode(@NonNull final String icon,@NonNull final Node node) {
		final FluentToggleButton toggleButton = new FluentToggleButton(icon).classes("primary", "icon");
		titleBar.append(Side.RIGHT, toggleButton);
		toggleButton.selectedProperty().addListener((o,old,value) -> {
			if(value) {
				titleBar.append(Side.BOTTOM, node);
			}else {
				titleBar.remove(Side.BOTTOM, node);
			}
		});
		return this;
	}

	@Override
	public Node getNode() {
		return titleBar	;
	}
	
}
