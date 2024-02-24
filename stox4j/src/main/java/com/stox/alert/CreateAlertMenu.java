package com.stox.alert;

import org.greenrobot.eventbus.EventBus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class CreateAlertMenu extends MenuItem implements EventHandler<ActionEvent> {

	private final EventBus eventBus;
	private final AlertService alertService;
	
	public CreateAlertMenu(EventBus eventBus, AlertService alertService) {
		this.eventBus = eventBus;
		this.alertService = alertService;
		setText("Create alert");
		setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		
	}
	
}
