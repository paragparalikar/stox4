package com.stox.module.data;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.fluent.scene.control.FluentListView;
import com.stox.fx.fluent.scene.control.FluentProgressBar;
import com.stox.fx.fluent.scene.control.FluentProgressIndicator;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ExchangeRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.module.data.downloader.scrip.ScripMasterDownloadAction;
import com.stox.workbench.module.ModuleTitleBar;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class DataView extends ModuleView<DataViewState> {

	@Getter
	private final ModuleTitleBar titleBar = new ModuleTitleBar();
	private volatile DownloadContext context;
	private final FxMessageSource messageSource;
	private final ExecutorService executorService;
	private final BarRepository barRepository;
	private final ScripRepository scripRepository;
	private final ExchangeRepository exchangeRepository;
	private final FluentProgressIndicator progressIndicator = new FluentProgressIndicator().classes("tiny","primary","inverted", "transparent-background");
	private final FluentListView<StatusMessage> listView = new FluentListView<StatusMessage>().cellFactory(a -> new StatusMessageListCell(progressIndicator));
	private final FluentComboBox<Exchange> exchangeComboBox = new FluentComboBox<Exchange>().items(Exchange.values()).select(Exchange.NSE).fullArea().classes("primary","inverted", "first");
	private final FluentButton actionButton = new FluentButton(Icon.DOWNLOAD).defaultButton(true).classes("primary", "inverted", "icon", "last").onAction(e -> action());
	private final FluentHBox controlsBox = new FluentHBox(exchangeComboBox, actionButton).fillHeight(true).fullWidth().classes("primary-background", "box", "padded");
	private final FluentProgressBar progressBar = new FluentProgressBar(0).fullWidth().classes("primary","success");
	private final FluentHBox infoBox = new FluentHBox(progressBar).fillHeight(true).fullWidth().classes("primary-background","padded");
	private final FluentBorderPane container = new FluentBorderPane().top(controlsBox).center(listView).bottom(infoBox);
	
	@Builder
	public DataView(@NonNull final FxMessageSource messageSource, 
			@NonNull final ExecutorService executorService, 
			@NonNull final BarRepository barRepository,
			@NonNull final ScripRepository scripRepository, 
			@NonNull final ExchangeRepository exchangeRepository) {
		this.barRepository = barRepository;
		this.messageSource = messageSource;
		this.executorService = executorService;
		this.scripRepository = scripRepository;
		this.exchangeRepository = exchangeRepository;
		
		title(titleBar);
		content(container);
		updateControls();
	}
	
	private void action() {
		if (isRunning()) {
			if (null != context) {
				context.setCancelled(true);
			}
		} else {
			final Exchange exchange = exchangeComboBox.getValue();
			final Calendar lastDownloadDate = getLastDownloadDate(exchange);
			context = DownloadContext.builder()
					.lastDownloadDate(lastDownloadDate)
					.initialLastDownloadDate(lastDownloadDate.getTimeInMillis())
					.messageSource(messageSource)
					.barRepository(barRepository)
					.exchangeRepository(exchangeRepository)
					.scripRepository(scripRepository)
					.executorService(executorService)
					.terminationCallback(this::terminate)
					.messageCallback(this::message)
					.afterCallback(this::after)
					.exchange(exchange)
					.build();
			progressBar.setProgress(0);
			listView.getItems().clear();
			executorService.submit(new ScripMasterDownloadAction(context));
		}
		updateControls();
	}

	private void message(StatusMessage message) {
		Ui.fx(() -> listView.getItems().add(message));
	}
	
	private void after(){
		final double start = (double) context.getInitialLastDownloadDate();
		final double end = (double) System.currentTimeMillis();
		final double current = (double) context.getLastDownloadDate().getTimeInMillis();
		final double value = (current - start)/(end - start);
		Ui.fx(() -> progressBar.setProgress(value));
	}
	
	private void terminate(){
		updateControls();
		if(context.isCancelled()){
			Ui.fx(() -> progressBar.setProgress(1));
		}
		context = null;
	}

	private boolean isRunning() {
		return null != context && !context.isCancelled();
	}

	private void updateControls() {
		Ui.fx(() -> {
			final boolean running = isRunning();
			exchangeComboBox.disable(running);
			actionButton.text(running ? Icon.STOP : Icon.DOWNLOAD).classes(running, "danger").classes(!running, "primary");
			context = running ? context : null;
		});
	}

	@SuppressWarnings("deprecation")
	private Calendar getLastDownloadDate(Exchange exchange) {
		final Date date = Optional.ofNullable(exchangeRepository.readLastDownloadDate(exchange)).orElseGet(() -> new Date(95,0,1));
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	@Override
	public DataViewState stop(Bounds bounds) {
		if (null != context) {
			context.setCancelled(true);
		}
		return stop(new DataViewState(), bounds);
	}

}
