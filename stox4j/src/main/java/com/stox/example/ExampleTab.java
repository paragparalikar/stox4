package com.stox.example;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.persistence.SerializationService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.common.ui.View;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ExampleTab extends Tab implements View {

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
	
	@Override public void loadView() { exampleView.loadView(); }
	@Override public void unloadView() { exampleView.unloadView(); }
	
}
