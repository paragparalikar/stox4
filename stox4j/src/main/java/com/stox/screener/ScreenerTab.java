package com.stox.screener;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.bar.BarService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ScreenerTab extends Tab {

	private final EventBus eventBus;
	private final BarService barService;
	private final ScripService scripService;
	
	public ScreenerTab(EventBus eventBus, BarService barService, ScripService scripService) {
		super("Screener");
		this.eventBus = eventBus;
		this.barService = barService;
		this.scripService = scripService;
		final Label graphics = new Label(Icon.FILTER);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setOnSelectionChanged(event -> onSelected());
	}
	
	private void onSelected() {
		if(isSelected() && null == getContent()) {
			setContent(new ScreenerView(eventBus, barService, scripService));
		}
	}
	
}
