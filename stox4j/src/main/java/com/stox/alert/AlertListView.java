package com.stox.alert;

import java.text.NumberFormat;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.alert.event.AlertCreatedEvent;
import com.stox.alert.event.AlertSatisfiedEvent;
import com.stox.alert.event.AlertSelectedEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class AlertListView extends ListView<Alert> {
	
	private final EventBus eventBus;
	private final AlertService alertService;
	private final ScripService scripService;

	public AlertListView(EventBus eventBus, AlertService alertService, ScripService scripService) {
		this.eventBus = eventBus;
		this.alertService = alertService;
		this.scripService = scripService;
		setCellFactory(this::createAlertCell);
		getSelectionModel().selectedItemProperty().addListener(this::changed);
		eventBus.register(this);
	}
	
	private void changed(ObservableValue<? extends Alert> observable, 
			Alert oldValue, Alert newValue) {
		Optional.ofNullable(newValue)
			.map(AlertSelectedEvent::new)
			.ifPresent(eventBus::post);
	}
	
	@Subscribe
	public void onAlertCreated(AlertCreatedEvent event) {
		Platform.runLater(() -> getItems().add(event.getAlert()));
	}
	
	@Subscribe
	public void onAlertSatisfied(AlertSatisfiedEvent event) {
		refresh();
	}
	
	private ListCell<Alert> createAlertCell(ListView<Alert> listView){
		return new ListCell<Alert>() {
			private final Button deleteButton = new Button(Icon.TRASH);
			{deleteButton.getStyleClass().setAll("icon", "button");}
			@Override
			protected void updateItem(Alert item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || null == item) {
					setText(null);
					setGraphic(null);
					getStyleClass().remove("success");
				} else {
					final Scrip scrip = scripService.findByIsin(item.getIsin());
					final String scripName = null == scrip ? item.getIsin() : scrip.getName();
					setText(scripName + " - " + NumberFormat.getCurrencyInstance().format(item.getPrice()));
					if(item.isSatisfied()) getStyleClass().add("success");
					setGraphic(deleteButton);
					deleteButton.setOnAction(event -> {
						alertService.delete(item);
						getItems().remove(item);
					});
				}
			}
		};
	}
}
