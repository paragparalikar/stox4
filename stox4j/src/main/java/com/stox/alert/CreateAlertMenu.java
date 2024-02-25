package com.stox.alert;

import org.greenrobot.eventbus.EventBus;
import org.ta4j.core.Bar;

import com.stox.common.event.SelectedBarQueryEvent;
import com.stox.common.scrip.Scrip;

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
		final double screenX = getParentPopup().getX();
		final double screenY = getParentPopup().getY();
		final SelectedBarQueryEvent selectedBarQueryEvent = new SelectedBarQueryEvent(screenX, screenY);
		eventBus.post(selectedBarQueryEvent);
		final Bar bar = selectedBarQueryEvent.getBar();
		final Scrip scrip = selectedBarQueryEvent.getScrip();
	}
	
}
