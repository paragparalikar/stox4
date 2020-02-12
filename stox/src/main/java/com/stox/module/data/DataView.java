package com.stox.module.data;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.stox.fx.fluent.beans.binding.FluentStringBinding;
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
import com.stox.module.core.model.intf.Action;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ExchangeRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.module.data.downloader.bar.eod.EodBarDownloadAction;
import com.stox.module.data.downloader.scrip.ScripMasterDownloadAction;
import com.stox.workbench.module.ModuleTitleBar;
import com.stox.workbench.module.ModuleView;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

public class DataView extends ModuleView<DataViewState> {
	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);

	private final FxMessageSource messageSource;
	private final ExecutorService executorService;
	private final BarRepository barRepository;
	private final ScripRepository scripRepository;
	private final ExchangeRepository exchangeRepository;
	private final BooleanProperty running = new SimpleBooleanProperty(false);

	@Getter
	private final ModuleTitleBar titleBar = new ModuleTitleBar();
	private final FluentProgressIndicator progressIndicator = new FluentProgressIndicator().classes("tiny", "primary", "inverted", "transparent-background");
	private final FluentListView<StatusMessage> listView = new FluentListView<StatusMessage>().cellFactory(a -> new StatusMessageListCell(progressIndicator));
	private final FluentComboBox<Exchange> exchangeComboBox = new FluentComboBox<Exchange>().items(Exchange.values()).select(Exchange.NSE).fullArea().classes("primary", "inverted", "first");
	private final FluentButton actionButton = new FluentButton(Icon.DOWNLOAD).defaultButton(true).classes("primary", "inverted", "icon", "last").onAction(this::action);
	private final FluentHBox controlsBox = new FluentHBox(exchangeComboBox, actionButton).fillHeight(true).fullWidth().classes("primary-background", "box", "padded");
	private final FluentProgressBar progressBar = new FluentProgressBar(0).fullWidth().classes("primary", "success");
	private final FluentHBox infoBox = new FluentHBox(progressBar).fillHeight(true).fullWidth().classes("primary-background", "padded");
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
		exchangeComboBox.disableProperty().bind(running);
		actionButton.textProperty().bind(new FluentStringBinding(() -> running.get() ? Icon.STOP : Icon.DOWNLOAD, running));
		running.addListener((o,old,value) -> actionButton.classes(value, "danger").classes(!value, "primary"));
	}

	@SneakyThrows
	@SuppressWarnings("deprecation")
	private void action(final ActionEvent event) {
		if (running.get()) {
			running.set(Boolean.FALSE);
		} else {
			running.set(Boolean.TRUE);
			progressBar.setProgress(0);
			listView.getItems().clear();
			executorService.submit(() -> {
				try {
					scripMasterDownloadAction().run();
					final ExecutorService executor = Executors.newWorkStealingPool();
					final Date lastDownloadDate = Optional.ofNullable(exchangeRepository.readLastDownloadDate(exchangeComboBox.value())).orElseGet(() -> new Date(95, 0, 1));
					final Calendar calendar = Calendar.getInstance();
					calendar.setTime(lastDownloadDate);
					while(running.get() && calendar.before(Calendar.getInstance())) {
						eodBarDownloadAction(calendar.getTime(), executor).run();
						calendar.add(Calendar.DATE, 1);
						updateProgressBar(lastDownloadDate, calendar.getTime());
					}
					executor.shutdown();
					executor.awaitTermination(10, TimeUnit.SECONDS);
				}catch(Throwable e) {
					e.printStackTrace();
				}finally {
					Ui.fx(() -> running.set(Boolean.FALSE));
				}
			});
		}
	}
	
	private Action scripMasterDownloadAction() {
		final StatusMessage message = new StatusMessage(messageSource.get("Download scrip master"));
		return ScripMasterDownloadAction.builder()
				.scripRepository(scripRepository)
				.exchange(exchangeComboBox.getValue())
				.exchangeRepository(exchangeRepository)
				.before(() -> Ui.fx(() -> listView.getItems().add(message)))
				.success(() -> Ui.fx(() -> message.success(Boolean.TRUE)))
				.failure(() -> Ui.fx(() -> message.success(Boolean.FALSE)))
				.build();
	}
	
	private Action eodBarDownloadAction(final Date date, final ExecutorService executorService) {
		final ObservableValue<String> textValue = messageSource.get("Download EOD bars for ");
		final StatusMessage message = new StatusMessage(new FluentStringBinding(() -> textValue.getValue() + DATE_FORMAT.format(date), textValue));
		return EodBarDownloadAction.builder()
				.date(date)
				.exchange(exchangeComboBox.value())
				.executorService(executorService)
				.barRepository(barRepository)
				.scripRepository(scripRepository)
				.exchangeRepository(exchangeRepository)
				.before(() -> Ui.fx(() -> listView.getItems().add(message)))
				.success(() -> Ui.fx(() -> message.success(Boolean.TRUE)))
				.failure(() -> Ui.fx(() -> message.success(Boolean.FALSE)))
				.build();
	}

	private void updateProgressBar(final Date startDate, final Date currentDate) {
		final double start = (double) startDate.getTime();
		final double end = (double) System.currentTimeMillis();
		final double current = (double) currentDate.getTime();
		final double value = (current - start) / (end - start);
		Ui.fx(() -> progressBar.setProgress(value));
	}

	@Override
	public DataViewState stop(Bounds bounds) {
		running.set(Boolean.FALSE);
		return stop(new DataViewState(), bounds);
	}

}
