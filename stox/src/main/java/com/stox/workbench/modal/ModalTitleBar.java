package com.stox.workbench.modal;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Spacer;
import com.stox.fx.widget.TitleBar;

import javafx.geometry.Side;
import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.Getter;

public class ModalTitleBar implements HasNode<Node>  {

	final FluentLabel graphic = new FluentLabel().classes("primary", "icon");
	final FluentLabel titleLabel = new FluentLabel().classes("primary", "title-text").fullHeight();
	final FluentButton closeButton = new FluentButton(Icon.TIMES).classes("primary", "icon", "hover-danger");
	@Getter(AccessLevel.PROTECTED)
	private final TitleBar titleBar = new TitleBar().append(Side.RIGHT, closeButton).center(new FluentHBox(graphic, titleLabel, new Spacer()));
	
	public ModalTitleBar() {
	}

	@Override
	public Node getNode() {
		return null;
	}

}
