package com.stox.ranker;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.bar.BarService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class RankerTab extends Tab {

	private final EventBus eventBus;
	private final BarService barService;
	private final ScripService scripService;
	
	public RankerTab(EventBus eventBus, BarService barService, ScripService scripService) {
		super("Ranker");
		this.eventBus = eventBus;
		this.barService = barService;
		this.scripService = scripService;
		final Label graphics = new Label(Icon.SORT);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setOnSelectionChanged(event -> onSelected());
	}
	
	private void onSelected() {
		if(isSelected() && null == getContent()) {
			setContent(new RankerView(eventBus, barService, scripService));
		}
	}
	
}
