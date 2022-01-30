package com.stox.chart;

import org.springframework.stereotype.Component;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.model.WorkbenchModule;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;

@Component
public class ChartWorkbenchModule extends WorkbenchModule {
	
	private SplitPane splitPane = new SplitPane();
	
	public ChartWorkbenchModule(ScripListPane scripListPane) {
		super("Chart", FontAwesomeIcon.LINE_CHART);
		splitPane.getItems().addAll(scripListPane, new StackPane(new Label("Chart")));
		splitPane.setDividerPositions(0.2d, 0.8d);
	}
	
	@Override
	public void init(Workbench workbench) {
		super.init(workbench);
	}

	@Override
	public Node activate() {
		return splitPane;
	}

}
