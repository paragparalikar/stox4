package com.stox;

import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.data.donwloader.DownloaderModule;
import com.stox.util.JsonConverter;
import com.stox.workbench.Workbench;
import com.stox.workbench.module.Module;
import com.stox.workbench.module.UiModule;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Config config;
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
		final NumberFormat currencyFormat = NumberFormat.getInstance();
		currencyFormat.setGroupingUsed(true);
		currencyFormat.setMaximumFractionDigits(2);
		currencyFormat.setMinimumFractionDigits(0);
		config = Config.builder()
				.currencyFormat(currencyFormat)
				.dateFormat(new SimpleDateFormat("dd-MMM-yyyy"))
				.dateFormatFull(new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a"))
				.home(Paths.get(System.getProperty("user.home"), ".stox"))
				.build();
		final ScheduledExecutorService scheduledExecutorService = 
				Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
		context = Context.builder()
				.config(config)
				.jsonConverter(new JsonConverter())
				.messageSource(new FxMessageSource())
				.scheduledExecutorService(scheduledExecutorService)
				.build();
		modules = Arrays.asList(new DownloaderModule(context));
		workbench = new Workbench(context, modules.stream().filter(UiModule.class::isInstance).map(UiModule.class::cast).collect(Collectors.toList()));
		workbench.onShown(event -> modules.forEach(module -> scheduledExecutorService.submit(() -> module.start(context)))).show();
	}

	@Override
	public void stop() throws Exception {
		workbench.hide();
		final ScheduledExecutorService scheduledExecutorService = context.getScheduledExecutorService();
		modules.forEach(module -> scheduledExecutorService.submit(module::stop));
		scheduledExecutorService.shutdown();
		scheduledExecutorService.awaitTermination(3, TimeUnit.SECONDS);
		super.stop();
	}

}
