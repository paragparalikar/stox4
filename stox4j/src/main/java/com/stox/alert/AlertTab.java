package com.stox.alert;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.common.ui.View;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class AlertTab extends Tab implements View {
	
	private final AlertView alertView;

	public AlertTab(EventBus eventBus,
			ScripService scripService,
			AlertService alertService) {
		super("Alerts");
		final Label graphics = new Label(Icon.BELL_ALT);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		this.alertView = new AlertView(eventBus, scripService, alertService);
		setContent(alertView);
	}

	@Override
	public void loadView() {
		alertView.loadView();
	}
	
	@Override
	public void unloadView() {
		alertView.unloadView();
	}
	
}
