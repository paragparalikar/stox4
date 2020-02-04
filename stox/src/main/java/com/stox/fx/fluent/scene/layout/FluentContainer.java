package com.stox.fx.fluent.scene.layout;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.sun.javafx.css.PseudoClassState;

import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class FluentContainer {

	private final FluentVBox top = new FluentVBox();
	private final FluentLabel titleLabel = new FluentLabel();
	private final FluentLabel messageLabel = new FluentLabel();
	private final FluentBorderPane borderPane = new FluentBorderPane().fullArea();
	private final FluentHBox buttonBar = new FluentHBox().fullWidth().alignment(Pos.CENTER_RIGHT).spacing(20);

	public FluentContainer() {
	}

	public FluentContainer(String title, Node content) {
		title(title).content(content);
	}

	public FluentContainer(String title, Node content, Node... buttons) {
		title(title).content(content).buttons(buttons);
	}

	public FluentContainer clear() {
		borderPane.clear();
		buttonBar.children().clear();
		top.children().clear();
		titleLabel.setText(null);
		messageLabel.setText(null);
		return this;
	}

	public Node node() {
		return borderPane;
	}

	public FluentContainer classes(String... classes) {
		borderPane.classes(classes);
		return this;
	}

	public FluentContainer content(Node value) {
		borderPane.center(value);
		return this;
	}

	public FluentContainer buttons(Node... buttons) {
		buttonBar.children(buttons);
		if (null == borderPane.bottom()) {
			borderPane.bottom(buttonBar);
			BorderPane.setMargin(buttonBar, new Insets(20, 0, 0, 0));
		}
		return this;
	}

	public FluentContainer error(String value) {
		return message(value, PseudoClassState.getPseudoClass("danger"));
	}

	public FluentContainer success(String value) {
		return message(value, PseudoClassState.getPseudoClass("success"));
	}

	public FluentContainer message(String value, PseudoClass pseudoClass) {
		if (null == value || 0 == value.trim().length()) {
			messageLabel.setText(null);
			top.getChildren().remove(messageLabel);
			if (null == titleLabel.getText()) {
				borderPane.setTop(null);
			}
		} else {
			if (null == borderPane.getTop()) {
				borderPane.setTop(top);
			}
			if (!top.getChildren().contains(messageLabel)) {
				top.getChildren().add(messageLabel);
			}
			messageLabel.text(value).pseudoClassState(pseudoClass, true);
		}
		return this;
	}

	public FluentContainer title(String value, String... classes) {
		if (null == value || 0 == value.trim().length()) {
			titleLabel.setText(null);
			top.getChildren().remove(titleLabel);
			if (null == messageLabel.getText()) {
				borderPane.setTop(null);
			}
		} else {
			if (null == borderPane.getTop()) {
				borderPane.setTop(top);
			}
			if (!top.getChildren().contains(titleLabel)) {
				top.getChildren().add(0, titleLabel);
			}
			titleLabel.getStyleClass().clear();
			titleLabel.text(value).classes("label").classes(classes);
		}
		return this;
	}

}
