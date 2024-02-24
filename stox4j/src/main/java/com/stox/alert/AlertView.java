package com.stox.alert;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;
import com.stox.common.ui.View;

import javafx.scene.layout.BorderPane;

public class AlertView extends BorderPane implements View {
	
	private final ScripService scripService;
	private final AlertService alertService;
	private final AlertListView alertListView;
	
	public AlertView(EventBus eventBus, 
			ScripService scripService, 
			AlertService alertService) {
		this.scripService = scripService;
		this.alertService = alertService;
		alertListView = new AlertListView(eventBus, alertService, scripService);
		setCenter(alertListView);
	}
	
	@Override
	public void loadView() {
		final Collection<Alert> alerts = alertService.findAll();
		final List<Alert> sortedAlerts = alerts.stream()
				.sorted(Comparator.comparing(alert -> scripService.findByIsin(alert.getIsin())))
				.collect(Collectors.toList());
		alertListView.getItems().addAll(sortedAlerts);
	}

}
