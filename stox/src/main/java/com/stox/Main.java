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

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.core.CoreModule;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ExchangeRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.module.explorer.ExplorerModule;
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
	private final List<? extends Module> modules = Arrays.asList(new CoreModule(context), new ExplorerModule(context));

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
		module.start();
	}
	
	private void stop(final Module module) {
		scheduledExecutorService.submit(module::stop);
	}
	
	private Context buildContext() {
		final Config config = buildConfig();
		final Path home = config.getHome();
		return Context.builder()
				.config(config)
				.workbench(workbench)
				.jsonConverter(jsonConverter)
				.messageSource(messageSource)
				.scheduledExecutorService(scheduledExecutorService)
				.exchangeRepository(new ExchangeRepository(home))
				.scripRepository(new ScripRepository(home))
				.barRepository(new BarRepository(home))
				.build();
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
