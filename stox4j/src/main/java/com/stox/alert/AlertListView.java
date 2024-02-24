package com.stox.alert;

import org.greenrobot.eventbus.EventBus;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class AlertListView extends ListView<Alert> {

	public AlertListView(EventBus eventBus) {
		// TODO Auto-generated constructor stub
	}

	public AlertListView(ObservableList<Alert> items) {
		super(items);
		// TODO Auto-generated constructor stub
	}

}
