package com.stox.alert;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.common.ui.View;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class AlertTab extends Tab implements View {
	
	private final AlertView alertView;
	private final ScheduledFuture<?> future;

	public AlertTab(EventBus eventBus,
			ScripService scripService,
			AlertService alertService,
			ScheduledExecutorService scheduledExecutorService) {
		super("Alerts");
		final Label graphics = new Label(Icon.BELL_ALT);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		this.alertView = new AlertView(eventBus, scripService, alertService);
		setContent(alertView);
		future = scheduledExecutorService.scheduleWithFixedDelay(alertService::poll, 10, 60, TimeUnit.SECONDS);
	}

	@Override
	public void loadView() {
		alertView.loadView();
	}
	
	@Override
	public void unloadView() {
		alertView.unloadView();
		future.cancel(true);
	}
	
}
