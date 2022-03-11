package com.stox.charting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

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
import com.stox.common.event.ScripSelectionEvent;
import com.stox.common.event.SelectedBarQueryEvent;
import com.stox.common.event.SelectedScripQueryEvent;
import com.stox.common.scrip.Scrip;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	
	@Getter
	public static class ChartingConfig {
		private int fetchSize = 200;
		private double maxUnitWidthProperty = 50;
		private double minUnitWidthProperty = 1;
		private double paddingTopProperty = 10;
		private double paddingBottomProperty = 8;
	}
	
	@Getter
	public static class ChartingContext {
		private final ObjectProperty<Scrip> scripProperty = new SimpleObjectProperty<>();
		private final ObjectProperty<BarSeries> barSeriesProperty = new SimpleObjectProperty<>(new BaseBarSeries());
		public Bar getBar(int index) {
			final BarSeries barSeries = barSeriesProperty.get();
			return null != barSeries && 0 <= index && index < barSeries.getBarCount() ? barSeries.getBar(index) : null;
		}
		public int getBarCount() {
			final BarSeries barSeries = barSeriesProperty.get();
			return null == barSeries ? 0 : barSeries.getBarCount();
		}
	}
	
	private final Chart priceChart;
	private final PricePlot pricePlot;
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
	
	public ChartingView(EventBus eventBus, BarService barService) {
		add(priceChart = new Chart(this));
		add(pricePlot = new PricePlot(barService));
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
	public void onScripSelected(ScripSelectionEvent event) {
		context.getScripProperty().set(event.getScrip());
	}
	
	@Subscribe
	public void onSelectedScripQuery(SelectedScripQueryEvent event) {
		event.setScrip(context.getScripProperty().get());
		if(event instanceof SelectedBarQueryEvent) {
			final SelectedBarQueryEvent selectedBarQueryEvent = SelectedBarQueryEvent.class.cast(event);
			final double x = selectedBarQueryEvent.getScreenX();
			final double y = selectedBarQueryEvent.getScreenY();
			final Point2D point = priceChart.getContentArea().screenToLocal(x, y);
			final int index = xAxis.getIndex(point.getX());
			final Bar bar = context.getBar(index);
			selectedBarQueryEvent.setBar(bar);
		}
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
