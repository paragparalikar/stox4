package com.stox.common.ui.modal;

import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Modal {

	private final Pane glass = new Pane();
	private final StackPane container = new StackPane();
	private final TitleBar titleBar = new TitleBar();
	private final ButtonBar buttonBar = new ButtonBar();
	private final Button closeButton = new Button(Icon.TIMES);
	
	private final BorderPane root = new BorderPane(container, titleBar, null, buttonBar, null);
	private final VBox rootWrapper = new VBox(root);
	private final StackPane modal = new StackPane(glass, rootWrapper);
	
	public Modal() {
		glass.getStyleClass().add("glass");
		root.getStyleClass().add("modal");
		container.getStyleClass().add("container");
		closeButton.getStyleClass().addAll("icon");
		titleBar.getRightItems().getChildren().add(closeButton);
		rootWrapper.setAlignment(Pos.CENTER);
		rootWrapper.setFillWidth(false);
		closeButton.setOnAction(event -> hide());
	}
	
	public Modal setContent(Node node) {
		container.getChildren().setAll(node);
		return this;
	}
	
	public Modal show(Node node) {
		final StackPane stackPane = (StackPane) node.getScene().getRoot();
		stackPane.getChildren().add(modal);
		return this;
	}
	
	public Modal hide() {
		Fx.run(() -> {
			final StackPane stackPane = (StackPane) modal.getParent();
			if(null != stackPane) stackPane.getChildren().remove(modal);
		});
		return this;
	}
	
	public Modal withTitleIcon(String icon) {
		titleBar.getLeftItems().getChildren().add(0, Fx.icon(icon));
		return this;
	}
	
	public Modal withTitleText(String text) {
		final Label titleText = new Label(text);
		titleText.getStyleClass().add("title-label");
		titleBar.getLeftItems().getChildren().add(titleText);
		return this;
	}
	
	public Modal withContent(Node content) {
		setContent(content);
		return this;
	}
	
	public Modal withButton(Button button) {
		buttonBar.getRightItems().getChildren().add(button);
		return this;
	}
}
