package com.stox.example;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;

import javafx.scene.layout.BorderPane;

public class ExampleView extends BorderPane {

	private final ExampleGroupComboBox exampleGroupComboBox;
	
	public ExampleView(EventBus eventBus, ScripService scripService, ExampleService exampleService,
			ExampleGroupService exampleGroupService) {
		final ExampleListView exampleListView = new ExampleListView(eventBus, scripService, exampleService);
		exampleGroupComboBox = new ExampleGroupComboBox(eventBus, exampleGroupService);
		final ExampleGroupControlsMenuButton exampleGroupControlsMenuButton = new ExampleGroupControlsMenuButton(exampleService,
				exampleGroupService, exampleGroupComboBox);
		final ExampleGroupTitleBar exampleGroupTitleBar = new ExampleGroupTitleBar(exampleGroupComboBox, exampleGroupControlsMenuButton);
		setTop(exampleGroupTitleBar);
		setCenter(exampleListView);
	}
	
	public void init() {
		exampleGroupComboBox.init();
	}
	
}
