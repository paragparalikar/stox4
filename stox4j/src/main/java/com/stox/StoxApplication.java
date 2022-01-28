package com.stox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dlsc.workbenchfx.Workbench;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class StoxApplication extends Application {

	public static void main(String[] args) {
		launch(args);
		SpringApplication.run(StoxApplication.class);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		final Workbench workbench = Workbench.builder().build();
		final Scene myScene = new Scene(workbench);
	    primaryStage.setScene(myScene);
	    primaryStage.setMaximized(true);
	    primaryStage.show();
	}
	
}
