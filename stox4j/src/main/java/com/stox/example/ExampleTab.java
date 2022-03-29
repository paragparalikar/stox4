package com.stox.example;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.SerializationService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ExampleTab extends Tab {

	private final ExampleView exampleView;
	
	public ExampleTab(
			EventBus eventBus, 
			ScripService scripService, 
			ExampleService exampleService,
			ExampleGroupService exampleGroupService,
			SerializationService serializationService) {
		super("Examples");
		final Label graphics = new Label(Icon.LIGHTBULB_ALT);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		this.exampleView = new ExampleView(eventBus, scripService, 
				exampleService, exampleGroupService, serializationService);
		setContent(exampleView);
	}
	
	public void load() { exampleView.load(); }
	public void show() { exampleView.show(); }
	public void unload() { exampleView.unload(); }
	
}
