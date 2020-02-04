package com.stox;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.workbench.Module;
import com.stox.fx.workbench.Workbench;
import com.stox.module.data.donwloader.DownloaderModule;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private Context context;
	private Workbench workbench;
	private List<? extends Module> modules;
	
	@Override
	public void init() throws Exception {
		super.init();
		Font.loadFont(Icon.class.getClassLoader().getResource(Icon.PATH).toExternalForm(), 10);
	}

	@Override
	public void start(Stage ignored) throws Exception {
		context = Context.builder()
				.messageSource(new FxMessageSource())
				.scheduledExecutorService(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()))
				.build();
		workbench = new Workbench(context, modules = Arrays.asList(new DownloaderModule(context)));
		workbench.show();
	}
	
	@Override
	public void stop() throws Exception {
		workbench.hide();
		context.getScheduledExecutorService().shutdown();
		context.getScheduledExecutorService().awaitTermination(3, TimeUnit.SECONDS);
		super.stop();
	}
	
}
