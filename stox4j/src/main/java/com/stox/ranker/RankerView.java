package com.stox.ranker;


import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger.SystemOutLogger;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.common.bar.BarService;
import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.ConfigView;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.Modal;
import com.stox.indicator.VolatilityContractionIndicator;

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

public class RankerView extends BorderPane {

	private final EventBus eventBus;
	private final BarService barService;
	private final ScripService scripService;
	private final ListView<Rank> listView = new ListView<>();
	private final Button actionButton = new Button(Icon.PLAY);
	private final ComboBox<Ranker<?>> comboBox = new ComboBox<>(FXCollections.observableArrayList(
			new VolatilityContractionRanker()));
	private final HBox titleBar = new HBox(comboBox, actionButton);
	
	public RankerView(EventBus eventBus, BarService barService, ScripService scripService) {
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
		listView.getSelectionModel().selectedItemProperty().addListener(this::onRankSelected);
	}
	
	private void onRankSelected(ObservableValue<? extends Rank> observable, Rank oldValue, Rank rank) {
		if(null != rank) eventBus.post(new ScripSelectedEvent(rank.getScrip()));
	}
	
	private void action() {
		final Ranker ranker = comboBox.getValue();
		if(null != ranker) {
			final RankerConfig config = ranker.createConfig();
			final ConfigView configView = ranker.createConfigView(config);
			configView.populateView();
			final Button runButton = new Button("Rank");
			final Label graphics = new Label(Icon.PLAY);
			graphics.getStyleClass().add("icon");
			runButton.setGraphic(graphics);
			final Modal modal = new Modal()
					.withTitleIcon(Icon.GEAR)
					.withTitleText(ranker.toString())
					.withContent(configView.getNode())
					.withButton(runButton)
					.show(this);
			runButton.setOnAction(event -> {
				configView.populateModel();
				modal.hide();
				rank(ranker, config);
			});
		}
	}
	
	@SneakyThrows
	private void rank(Ranker ranker, RankerConfig config) {
		final List<Scrip> scrips = scripService.findAll();
		final ExecutorService executor = Executors.newWorkStealingPool();
		for(Scrip scrip : scrips) executor.execute(() -> rank(scrip, ranker, config));
		executor.shutdown();
	}
	
	private void rank(Scrip scrip, Ranker ranker, RankerConfig config) {
		final int barCount = config.getBarCount();
		final List<Bar> bars = barService.find(scrip.getIsin(), barCount);
		if(bars.size() >= barCount) {
			final BarSeries barSeries = new BaseBarSeries(bars);
			final Indicator<Num> indicator = new VolatilityContractionIndicator(barSeries, barCount);
			final Num rankValue = indicator.getValue(barSeries.getBarCount() - 1);
			Platform.runLater(() -> {
				listView.getItems().add(new Rank(rankValue, scrip));
				FXCollections.sort(listView.getItems(), Comparator.comparing(Rank::getValue));
			});
		}
	}
}
