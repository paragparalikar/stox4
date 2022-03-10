package com.stox.example;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ExampleTab extends Tab {
	
	private final EventBus eventBus;
	private final ScripService scripService;
	private final ExampleService exampleService;
	private final ExampleGroupService exampleGroupService;

	public ExampleTab(EventBus eventBus, ScripService scripService, ExampleService exampleService,
			ExampleGroupService exampleGroupService) {
		super("Examples");
		this.eventBus = eventBus;
		this.scripService = scripService;
		this.exampleService = exampleService;
		this.exampleGroupService = exampleGroupService;
		final Label graphics = new Label(Icon.LIGHTBULB_ALT);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setOnSelectionChanged(event -> init());
	}
	
	private void init() {
		if(isSelected() && null == getContent()) {
			final ExampleView exampleView = new ExampleView(eventBus, scripService, exampleService, exampleGroupService);
			setContent(exampleView);
			exampleView.init();
		}
	}
	
}
