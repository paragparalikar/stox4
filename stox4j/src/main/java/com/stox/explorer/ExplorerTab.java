package com.stox.explorer;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.persistence.SerializationService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.common.ui.View;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ExplorerTab extends Tab implements View {

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
	
	@Override public void loadView() {explorerView.loadView();}
	@Override public void unloadView() {explorerView.unloadView();}
	
}
