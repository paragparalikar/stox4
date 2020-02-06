package com.stox;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.stox.core.model.Exchange;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.data.donwloader.DownloaderModule;
import com.stox.persistence.store.DateFileStore;
import com.stox.util.JsonConverter;
import com.stox.workbench.Workbench;
import com.stox.workbench.module.Module;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private final JsonConverter jsonConverter = new JsonConverter();
	private final FxMessageSource messageSource = new FxMessageSource();
	private final Workbench workbench = new Workbench(messageSource);
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	private final Context context = buildContext();
	private final List<? extends Module> modules = Arrays.asList(new DownloaderModule(context));

	@Override
	public void init() throws Exception {
		super.init();
		Font.loadFont(Icon.class.getClassLoader().getResource(Icon.PATH).toExternalForm(), 10);
	}

	@Override
	public void start(Stage ignored) throws Exception {
		workbench.onShown(this::onWorkbenchShown).show();
	}
	
	private void onWorkbenchShown(final WindowEvent event) {
		modules.forEach(this::start);
	}
	
	private void start(final Module module) {
		scheduledExecutorService.submit(() -> module.start(context));
	}
	
	private void stop(final Module module) {
		scheduledExecutorService.submit(module::stop);
	}
	
	private Context buildContext() {
		final Context context =  Context.builder()
				.config(buildConfig())
				.workbench(workbench)
				.jsonConverter(jsonConverter)
				.messageSource(messageSource)
				.scheduledExecutorService(scheduledExecutorService)
				.build();
		Arrays.asList(Exchange.values()).forEach(exchange -> {
			final Path relativePath = Paths.get("exchanges", exchange.getCode().toLowerCase() + ".txt");
			final Path path = context.getConfig().getHome().resolve(relativePath);
			context.getLasdDownloadDateStore().put(exchange, new DateFileStore(path));
		});
		return context;
	}

	private Config buildConfig() {
		final NumberFormat currencyFormat = NumberFormat.getInstance();
		currencyFormat.setGroupingUsed(true);
		currencyFormat.setMaximumFractionDigits(2);
		currencyFormat.setMinimumFractionDigits(0);
		return Config.builder()
				.currencyFormat(currencyFormat)
				.dateFormat(new SimpleDateFormat("dd-MMM-yyyy"))
				.dateFormatFull(new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a"))
				.home(Paths.get(System.getProperty("user.home"), ".stox"))
				.build();
	}
	
	@Override
	public void stop() throws Exception {
		workbench.hide();
		modules.forEach(this::stop);
		scheduledExecutorService.shutdown();
		scheduledExecutorService.awaitTermination(3, TimeUnit.SECONDS);
		super.stop();
	}

}
