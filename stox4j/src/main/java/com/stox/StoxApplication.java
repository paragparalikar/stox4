package com.stox;

import com.stox.charting.ChartingView;
import com.stox.common.bar.BarRepository;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StoxApplication extends Application {
	
	public static void main(String[] args) {
		LauncherImpl.launchApplication(StoxApplication.class, null, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final BarRepository barRepository = new BarRepository();
		final BarService barService = new BarService(barRepository);
		final ChartingView chartingView = new ChartingView(barService);
		final Scrip scrip = Scrip.builder().isin("INE769A01020").code("AARTIIND").name("Aarti Industries Limited").build();
		chartingView.setScrip(scrip);
		primaryStage.setScene(new Scene(chartingView));
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		
	}
	
}
