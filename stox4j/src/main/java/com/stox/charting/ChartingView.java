package com.stox.charting;

import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.ta4j.core.Bar;

import com.stox.charting.axis.XAxis;
import com.stox.charting.chart.Chart;
import com.stox.charting.controls.ChartingButtonBar;
import com.stox.charting.crosshair.Crosshair;
import com.stox.charting.grid.VerticalGrid;
import com.stox.charting.handler.pan.PanRequestEvent;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.price.PricePlot;
import com.stox.charting.tools.IndicatorButton;
import com.stox.charting.tools.RuleButton;
import com.stox.common.bar.BarService;
import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.event.SelectedBarQueryEvent;
import com.stox.common.event.SelectedScripQueryEvent;
import com.stox.common.scrip.ScripService;
import com.stox.example.Example;
import com.stox.example.event.ExampleSelectedEvent;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Getter;


@Getter
public class ChartingView extends BorderPane {
	
	private final Chart priceChart;
	private final PricePlot pricePlot;
	private final ScripService scripService;
	private final ToolBar toolBar = new ToolBar();
	private final SplitPane splitPane = new SplitPane();
	private final ContextMenu contextMenu = new ContextMenu();
	private final ChartingConfig config = new ChartingConfig();
	private final VerticalGrid verticalGrid = new VerticalGrid();
	private final Crosshair crosshair = new Crosshair(splitPane);
	private final ChartingContext context = new ChartingContext();
	private final ChartingButtonBar controlButtons = new ChartingButtonBar(this);
	private final XAxis xAxis = new XAxis(context, config, crosshair, verticalGrid);
	
	private final StackPane stackPane = new StackPane(verticalGrid, splitPane, controlButtons, crosshair);
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(EventBus eventBus, BarService barService, ScripService scripService) {
		this.scripService = scripService;
		add(priceChart = new Chart(this));
		add(pricePlot = new PricePlot(barService));
		
		contextMenu.setAutoHide(true);
		contextMenu.setConsumeAutoHidingEvents(true);
		setOnContextMenuRequested(this::onContextMenuRequested);
		
		setCenter(stackPane);
		setBottom(new VBox(xAxis, toolBar));
		getStyleClass().add("charting-view");
		splitPane.setOrientation(Orientation.VERTICAL);
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
		toolBar.getItems().addAll(new RuleButton(this, context), new IndicatorButton(this, context));
		context.getBarSeriesProperty().addListener((o,old,value) -> redraw());
		crosshair.visibleProperty().bind(new BooleanBinding() {
			{bind(context.getBarSeriesProperty());}
			protected boolean computeValue() {
				return null != context.getBar(0);
			}
		});
		eventBus.register(this);
	}
	
	private void onContextMenuRequested(ContextMenuEvent event) {
		contextMenu.show(this, event.getScreenX(), event.getScreenY());
	}
	
	public void add(Chart chart) {
		charts.add(chart);
		splitPane.getItems().add(chart);
		for(int index = 1; index < charts.size(); index++) {
			splitPane.setDividerPosition(index - 1, 1 - (index * 0.2));
		}
	}
	
	public void remove(Chart chart) {
		charts.remove(chart);
		splitPane.getItems().remove(chart);
		redraw();
	}
	
	public void add(Plot<?, ?, ?> plot) {
		priceChart.add(plot);
		plot.reload();
		priceChart.redraw();
	}
	
	public void remove(Plot<?, ?, ?> plot) {
		priceChart.removePlot(plot);
	}
	
	@Subscribe
	public void onScripSelected(ScripSelectedEvent event) {
		context.getInputProperty().set(new ChartingInput(event.getScrip(), null));
	}
	
	@Subscribe
	public void onExampleSelected(ExampleSelectedEvent event) {
		Optional.ofNullable(event.getExample())
			.map(Example::getIsin)
			.map(scripService::findByIsin)
			.map(scrip -> new ChartingInput(scrip, event.getExample().getTimestamp()))
			.ifPresent(context.getInputProperty()::set);
	}
	
	@Subscribe
	public void onSelectedScripQuery(SelectedScripQueryEvent event) {
		event.setScrip(context.getScrip());
	}
	
	@Subscribe
	public void onSelectedBarQueryEvent(SelectedBarQueryEvent event) {
		final Point2D point = priceChart.getContentArea().screenToLocal(event.getScreenX(), event.getScreenY());
		final Bar bar = context.getBar(xAxis.getIndex(point.getX()));
		event.setBar(bar);
	}
	
	public void pan(PanRequestEvent event) {
		xAxis.pan(event.getDeltaX());
		redraw();
		pricePlot.reloadBars();
	}
	
	public void zoom(ZoomRequestEvent event) {
		xAxis.zoom(event.getX(), event.getPercentage());
		redraw();
		pricePlot.reloadBars();
	}
	
	public void redraw() {
		if(Platform.isFxApplicationThread()) {
			priceChart.redraw();
			charts.forEach(Chart::redraw);
		} else {
			Platform.runLater(() -> {
				priceChart.redraw();
				charts.forEach(Chart::redraw);
			});
		}
	}
}
