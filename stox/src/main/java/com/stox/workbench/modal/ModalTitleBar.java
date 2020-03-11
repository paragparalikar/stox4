package com.stox.workbench.modal;

import java.util.Optional;

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

public class ModalTitleBar implements HasNode<Node>  {
	
	final FluentLabel graphic = new FluentLabel().classes("primary", "icon");
	final FluentLabel titleLabel = new FluentLabel().classes("primary", "title-text").fullHeight();
	final FluentButton closeButton = new FluentButton(Icon.TIMES).classes("primary", "icon", "hover-danger");
	@Getter(AccessLevel.PROTECTED)
	private final TitleBar titleBar = new TitleBar().append(Side.RIGHT, closeButton).center(new FluentHBox(graphic, titleLabel, new Spacer()));
	
	public ModalTitleBar graphic(final String icon) {
		graphic.text(icon);
		return this;
	}
	
	public ModalTitleBar title(final ObservableValue<String> text) {
		titleLabel.textProperty().unbind();
		titleLabel.text(null);
		Optional.ofNullable(text).ifPresent(titleLabel.textProperty()::bind);
		return this;
	}
	
	protected ModalTitleBar closeEventHandler(@NonNull final EventHandler<ActionEvent> closeEventHandler) {
		closeButton.onAction(closeEventHandler);
		return this;
	}
	
	protected FluentToggleButton appendToggleNode(@NonNull final String icon,@NonNull final Node node) {
		final FluentToggleButton toggleButton = new FluentToggleButton(icon).classes("primary", "icon");
		titleBar.append(Side.RIGHT, toggleButton);
		toggleButton.selectedProperty().addListener((o,old,value) -> {
			if(value) {
				titleBar.append(Side.BOTTOM, node);
			}else {
				titleBar.remove(Side.BOTTOM, node);
			}
		});
		return toggleButton;
	}

	@Override
	public Node getNode() {
		return titleBar.getNode()	;
	}
	
}
