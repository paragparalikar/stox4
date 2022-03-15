package com.stox.screener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.greenrobot.eventbus.EventBus;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Rule;

import com.stox.common.bar.BarService;
import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.Modal;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.SneakyThrows;

public class ScreenerView extends BorderPane {
	
	private final EventBus eventBus;
	private final BarService barService;
	private final ScripService scripService;
	private final ListView<Scrip> listView = new ListView<>();
	private final Button actionButton = new Button(Icon.PLAY);
	private final ComboBox<Screener<?>> comboBox = new ComboBox<>(FXCollections.observableArrayList(
			new VolatilityContractionBreakoutScreener()));
	private final HBox titleBar = new HBox(comboBox, actionButton);

	public ScreenerView(EventBus eventBus, BarService barService, ScripService scripService) {
		this.eventBus = eventBus;
		this.barService = barService;
		this.scripService = scripService;
		comboBox.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(comboBox, Priority.ALWAYS);
		actionButton.getStyleClass().add("icon");
		actionButton.setOnAction(event -> action());
		setTop(titleBar);
		setCenter(listView);
		comboBox.getSelectionModel().select(0);
		listView.getSelectionModel().selectedItemProperty().addListener(this::onScripSelected);
	}
	
	private void onScripSelected(ObservableValue<? extends Scrip> observable, Scrip oldValue, Scrip scrip) {
		if(null != scrip) eventBus.post(new ScripSelectedEvent(scrip));
	}
	
	private void action() {
		final Screener screener = comboBox.getValue();
		if(null != screener) {
			final ScreenerConfig config = screener.createConfig();
			final ConfigView configView = screener.createConfigView(config);
			configView.populateView();
			final Button runButton = new Button("Screen");
			final Label graphics = new Label(Icon.FILTER);
			graphics.getStyleClass().add("icon");
			runButton.setGraphic(graphics);
			final Modal modal = new Modal()
					.withTitleIcon(Icon.GEAR)
					.withTitleText(screener.toString())
					.withContent(configView.getNode())
					.withButton(runButton)
					.show(this);
			runButton.setOnAction(event -> {
				configView.populateModel();
				modal.hide();
				screen(screener, config);
			});
		}
	}
	
	@SneakyThrows
	private void screen(Screener screener, ScreenerConfig config) {
		final List<Scrip> scrips = scripService.findAll();
		final ExecutorService executor = Executors.newWorkStealingPool();
		for(Scrip scrip : scrips) executor.execute(() -> screen(scrip, screener, config));
		executor.shutdown();
	}

	private void screen(Scrip scrip, Screener screener, ScreenerConfig config) {
		final int barCount = config.getBarCount();
		final List<Bar> bars = barService.find(scrip.getIsin(), barCount);
		if(bars.size() >= barCount) {
			final BarSeries barSeries = new BaseBarSeries(bars);
			final Rule rule = screener.createRule(config, barSeries);
			if(rule.isSatisfied(barSeries.getBarCount() - 1)) {
				Platform.runLater(() -> {
					listView.getItems().add(scrip);
					FXCollections.sort(listView.getItems());
				});
			}
		}
	}
	
}