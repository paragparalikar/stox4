package com.stox.explorer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.stox.charting.ChartingView;
import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

@Component
public class ExplorerWorkbenchModule extends WorkbenchModule {

	private ChartingView chartingView;
	@Autowired private BarService barService;
	@Autowired private ExplorerScripListView explorerListView;
	private final SplitPane splitPane = new SplitPane();
	
	public ExplorerWorkbenchModule() {
		super("Instrument Explorer", FontAwesomeIcon.WPEXPLORER);
	}
	
	@PostConstruct
	public void init() {
		chartingView = new ChartingView(barService);
		splitPane.getItems().addAll(explorerListView, chartingView);
		splitPane.setDividerPositions(0.2d, 0.8d);
		explorerListView.getSelectionModel().selectedItemProperty().addListener(this::onScripChanged);
	}
	
	private void onScripChanged(ObservableValue<? extends Scrip> observable, Scrip oldValue, Scrip newValue) {
		if(null != newValue) {
			chartingView.setScrip(newValue);
		}
	}

	@Override
	public Node activate() {
		return splitPane;
	}

}
