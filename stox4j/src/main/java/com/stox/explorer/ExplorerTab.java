package com.stox.explorer;

import com.stox.charting.ChartingView;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class ExplorerTab extends Tab {

	private final ChartingView chartingView;
	private final ScripService scripService;
	
	public ExplorerTab(ChartingView chartingView, ScripService scripService) {
		super("Explorer");
		this.chartingView = chartingView;
		this.scripService = scripService;
		final Label graphics = new Label(Icon.LIST);
		graphics.getStyleClass().add("icon");
		setGraphic(graphics);
		setOnSelectionChanged(event -> init());
	}
	
	private void init() {
		if(isSelected() && null == getContent()) {
			setContent(new ExplorerView(scripService, chartingView));
		}
	}
	
}
