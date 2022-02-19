package com.stox.common.ui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class Dialogx extends StackPane {

	private final Pane glass = new Pane();
	private final StackPane container = new StackPane();
	@Getter private final TitleBar titleBar = new TitleBar();
	@Getter private final ButtonBar buttonBar = new ButtonBar();
	private final BorderPane root = new BorderPane(container, titleBar, null, buttonBar, null);
	
	public Dialogx() {
		glass.getStyleClass().add("glass");
		root.getStyleClass().add("dialog-root");
		container.getStyleClass().add("dialog-container");
		
		final VBox vBox = new VBox(root);
		vBox.setAlignment(Pos.CENTER);
		vBox.setFillWidth(false);
		getChildren().addAll(glass, vBox);
	}
	
	public void setContent(Node node) {
		container.getChildren().setAll(node);
	}
	
	public void show(Node node) {
		final StackPane stackPane = (StackPane) node.getScene().getRoot();
		stackPane.getChildren().add(this);
	}
	
	public void hide() {
		final StackPane stackPane = (StackPane) getParent();
		if(null != stackPane) stackPane.getChildren().remove(this);
	}
}
