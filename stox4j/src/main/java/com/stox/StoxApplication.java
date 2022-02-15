package com.stox;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dlsc.workbenchfx.Workbench;
import com.stox.explorer.ExplorerWorkbenchModule;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class StoxApplication extends Application {
	
	public static void main(String[] args) {
		LauncherImpl.launchApplication(StoxApplication.class, StoxPreloader.class, args);
	}
	
	@Override
	public void init() throws Exception {
		SpringApplication.run(StoxApplication.class);
	}
	
	@Bean
	public Workbench workbench(ExplorerWorkbenchModule explorerWorkbenchModule) {
		return Workbench
				.builder(explorerWorkbenchModule)
				.build();
	}
	
	@Bean
	public CommandLineRunner show(Workbench workbench) {
		return args -> {
			Platform.runLater(() -> {
				final Scene myScene = new Scene(workbench);
				final Stage stage = new Stage();
			    stage.setScene(myScene);
			    stage.setMaximized(true);
			    stage.show();
			});
		};
	}
	
	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		
	}
	
}
