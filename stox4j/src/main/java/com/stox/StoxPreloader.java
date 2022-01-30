package com.stox;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StoxPreloader extends Preloader {

	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setHeight(300);
		stage.setWidth(600);
		
		final BorderPane borderPane = new BorderPane(new Label("Initializing..."));
		borderPane.setBackground(new Background(new BackgroundFill(Color.DARKRED, null, null)));
		final Scene scene = new Scene(borderPane, 600, 300, Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {
		if (info.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
	}

}
