package com.stox.explorer;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.SerializationService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ExplorerTab extends Tab {

	private final ExplorerView explorerView;
	
	public ExplorerTab(
			EventBus eventBus, 
			ScripService scripService, 
			SerializationService serializationService) {
		super("Explorer");
		this.explorerView = new ExplorerView(eventBus, scripService, serializationService);
		final Label graphics = new Label(Icon.LIST);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setContent(explorerView);
	}
	
	public void load() {explorerView.load();}
	public void show() {explorerView.show();}
	public void unload() {explorerView.unload();}
	
}
