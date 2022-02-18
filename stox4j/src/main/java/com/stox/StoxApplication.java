package com.stox;

import java.util.concurrent.ThreadFactory;

import com.stox.charting.ChartingContext;
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
		final ChartingContext context = new ChartingContext();
		final ChartingView chartingView = new ChartingView(context, barService);
		final Scrip scrip = Scrip.builder().isin("INE769A01020").code("AARTIIND").name("Aarti Industries Limited").build();
		context.getScripProperty().set(scrip);
		primaryStage.setScene(new Scene(chartingView));
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
}

class DaemonThreadFactory implements ThreadFactory {
	
	private int counter;

	@Override
	public Thread newThread(Runnable runnable) {
		final Thread thread = new Thread(runnable, "Stox4j-executor-thread-" + ++counter);
		thread.setDaemon(true);
		return thread;
	}
	
}