package com.stox.explorer;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ExplorerTab extends Tab {

	private final EventBus eventBus;
	private final ScripService scripService;
	
	public ExplorerTab(EventBus eventBus, ScripService scripService) {
		super("Explorer");
		this.eventBus = eventBus;
		this.scripService = scripService;
		final Label graphics = new Label(Icon.LIST);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setOnSelectionChanged(event -> init());
	}
	
	private void init() {
		if(isSelected() && null == getContent()) {
			setContent(new ExplorerView(eventBus, scripService));
		}
	}
	
}
