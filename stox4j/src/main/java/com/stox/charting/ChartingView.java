package com.stox.charting;

import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.ta4j.core.Bar;

import com.stox.charting.axis.x.XAxis;
import com.stox.charting.axis.x.XAxisState;
import com.stox.charting.chart.Chart;
import com.stox.charting.controls.ChartingButtonBar;
import com.stox.charting.crosshair.Crosshair;
import com.stox.charting.drawing.DrawingButtonBar;
import com.stox.charting.drawing.DrawingService;
import com.stox.charting.grid.VerticalGrid;
import com.stox.charting.handler.pan.PanRequestEvent;
import com.stox.charting.handler.zoom.ZoomRequestEvent;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.indicator.PlottableVolumeIndicator;
import com.stox.charting.plot.price.PricePlot;
import com.stox.charting.tools.IndicatorButton;
import com.stox.charting.tools.RuleButton;
import com.stox.common.SerializationService;
import com.stox.common.bar.BarService;
import com.stox.common.event.ScripSelectedEvent;
import com.stox.common.event.SelectedBarQueryEvent;
import com.stox.common.event.SelectedScripQueryEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.View;
import com.stox.example.Example;
import com.stox.example.event.ExampleSelectedEvent;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;
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
public class ChartingView extends BorderPane implements View {
	
	private final Chart priceChart;
	private final PricePlot pricePlot;
	private final ScripService scripService;
	private final DrawingService drawingService;
	private final SerializationService serializationService;
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
	
	private ChartingViewState state;
	
	public ChartingView(Path home, 
			EventBus eventBus, 
			BarService barService, 
			ScripService scripService, 
			DrawingService drawingService,
			SerializationService serializationService) {
		this.scripService = scripService;
		this.drawingService = drawingService;
		this.serializationService = serializationService;
		
		add(priceChart = new Chart(this));
		add(pricePlot = new PricePlot(barService));
		new PlottableVolumeIndicator().add(this);
		
		contextMenu.setAutoHide(true);
		contextMenu.setConsumeAutoHidingEvents(true);
		setOnContextMenuRequested(this::onContextMenuRequested);
		
		setCenter(stackPane);
		setBottom(new VBox(xAxis, toolBar));
		getStyleClass().add("charting-view");
		splitPane.setOrientation(Orientation.VERTICAL);
		addEventHandler(PanRequestEvent.TYPE, this::pan);
		addEventHandler(ZoomRequestEvent.TYPE, this::zoom);
		toolBar.getItems().addAll(
				new DrawingButtonBar(this),
				new RuleButton(this, context, home), 
				new IndicatorButton(this, context));
		context.getArgumentsProperty().addListener(this::onArgumentsChanged);
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
		contextMenu.show(xAxis, event.getScreenX(), event.getScreenY());
	}
	
	public void add(Chart chart) {
		charts.add(chart);
		splitPane.getItems().add(chart);
		updateDividerPositions();
	}
	
	public void remove(Chart chart) {
		charts.remove(chart);
		splitPane.getItems().remove(chart);
		updateDividerPositions();
		redraw();
	}
	
	private void updateDividerPositions() {
		for(int index = charts.size() - 1; index > 0; index--) {
			final int dividerIndex = index - 1;
			final double dividerPosition = 1 - ((charts.size() - index) * 0.2);
			splitPane.setDividerPosition(dividerIndex, dividerPosition);
		}
	}
	
	public void add(Plot<?, ?, ?> plot) {
		priceChart.add(plot);
		plot.reload();
		priceChart.redraw();
	}
	
	public void remove(Plot<?, ?, ?> plot) {
		priceChart.removePlot(plot);
	}
	
	private void onArgumentsChanged(ObservableValue<? extends ChartingArguments> observable, ChartingArguments old, ChartingArguments value) {
		if(null != old && null != old.getScrip()) drawingService.save(old.getScrip().getIsin(), priceChart.getDrawings());
		priceChart.clearDrawings();
		if(null != value && null != value.getScrip()) drawingService.findByIsin(value.getScrip().getIsin(), priceChart).forEach(priceChart::add);
	}
	
	@Subscribe
	public void onScripSelected(ScripSelectedEvent event) {
		context.getArgumentsProperty().set(new ChartingArguments(event.getScrip(), null));
	}
	
	@Subscribe
	public void onExampleSelected(ExampleSelectedEvent event) {
		Optional.ofNullable(event.getExample())
			.map(Example::getIsin)
			.map(scripService::findByIsin)
			.map(scrip -> new ChartingArguments(scrip, event.getExample().getTimestamp()))
			.ifPresent(context.getArgumentsProperty()::set);
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
		Platform.runLater(() -> {
			priceChart.redraw();
			charts.forEach(Chart::redraw);
		});
	}
	
	@Override
	public void load() {
		this.state = serializationService.deserialize(ChartingViewState.class);
	}
	
	@Override
	public void show() {
		if(null != state) {
			final Scrip scrip = Optional.ofNullable(state.getIsin()).map(scripService::findByIsin).orElse(null);
			final ZonedDateTime to = 0 == state.getTo() ? null : ZonedDateTime.ofInstant(Instant.ofEpochMilli(state.getTo()), ZoneId.systemDefault());
			final ChartingArguments args = new ChartingArguments(scrip, to);
			context.getArgumentsProperty().set(args);
			Optional.ofNullable(state.getXAxisState()).ifPresent(xAxis::setState);
		}
	}
	
	@Override
	public void unload() {
		final String isin = Optional.ofNullable(context.getScrip()).map(Scrip::getIsin).orElse(null);
		final long to = Optional.ofNullable(context.getTo())
				.map(ZonedDateTime::toInstant)
				.map(Instant::toEpochMilli)
				.orElse(0l);
		final XAxisState xAxisState = xAxis.getState();
		final ChartingViewState state = new ChartingViewState(to, isin, xAxisState);
		serializationService.serialize(state);
	}

}
