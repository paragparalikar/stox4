package com.stox;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

import com.stox.alert.AlertTab;
import com.stox.alert.CreateAlertMenu;
import com.stox.charting.ChartingView;
import com.stox.common.persistence.SerializationService;
import com.stox.common.ui.Fx;
import com.stox.common.ui.MessagePanel;
import com.stox.common.ui.View;
import com.stox.example.AddAsExampleMenu;
import com.stox.example.ExampleTab;
import com.stox.explorer.ExplorerTab;
import com.stox.ranker.RankerTab;
import com.stox.screener.ScreenerTab;
import com.stox.watchlist.WatchlistTab;
import com.stox.watchlist.menu.AddToWatchlistMenu;

import javafx.geometry.Side;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.StackPane;

public class StoxApplicationRoot extends StackPane implements View {
	
	private final StoxApplicationContext context;
	private final ChartingView chartingView;
	private final ExplorerTab explorerTab;
	private final ExampleTab exampleTab;
	private final AlertTab alertTab;
	private final RankerTab rankerTab;
	private final ScreenerTab screenerTab;
	private final WatchlistTab watchlistTab;
	private final AddToWatchlistMenu addToWatchlistMenu;
	private final AddAsExampleMenu addAsExampleMenu;
	private final CreateAlertMenu createAlertMenu;
	
	private final TabPane tabPane;
	private final SplitPane splitPane;
	private final List<? extends View> views;
	
	private StoxApplicationState state;
	
	public StoxApplicationRoot(StoxApplicationContext context) {
		this.context = context;
		this.chartingView = new ChartingView(context.getHome(), context.getEventBus(), context.getBarService(), 
				context.getScripService(), context.getDrawingService(), context.getSerializationService());
		this.explorerTab = new ExplorerTab(context.getEventBus(), context.getScripService(), context.getSerializationService());
		this.rankerTab = new RankerTab(context.getEventBus(), context.getBarService(), context.getScripService());
		this.screenerTab = new ScreenerTab(context.getEventBus(), context.getBarService(), context.getScripService());
		this.watchlistTab = new WatchlistTab(context.getEventBus(), context.getScripService(), context.getWatchlistService(),
				context.getSerializationService());
		this.addToWatchlistMenu =  new AddToWatchlistMenu(context.getEventBus(), context.getWatchlistService());
		this.exampleTab = new ExampleTab(context.getEventBus(), context.getScripService(), 
				context.getExampleService(), context.getExampleGroupService(), context.getSerializationService());
		this.addAsExampleMenu = new AddAsExampleMenu(context.getEventBus(), context.getExampleService(), context.getExampleGroupService());
		this.alertTab = new AlertTab(context.getEventBus(), context.getScripService(), context.getAlertService(), context.getScheduledExecutorService());
		this.createAlertMenu = new CreateAlertMenu(context.getEventBus(), context.getAlertService());
		chartingView.getContextMenu().getItems().addAll(createAlertMenu, addToWatchlistMenu, addAsExampleMenu);
		chartingView.getToolBar().getItems().addAll(Fx.spacer(), new MessagePanel(context.getEventBus()));
		
		this.tabPane = new TabPane(explorerTab, watchlistTab, alertTab, exampleTab, screenerTab, rankerTab);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		this.splitPane = new SplitPane(tabPane, chartingView);
		getChildren().add(splitPane);
		
		views = Arrays.asList(addAsExampleMenu, addToWatchlistMenu, watchlistTab, explorerTab, alertTab, exampleTab, chartingView);
	}
	
	public void loadView() {
		final Executor executor = context.getExecutor();
		final SerializationService serializationService = context.getSerializationService();
		executor.execute(() -> { state = serializationService.deserialize(StoxApplicationState.class); });
		for(View view : views) executor.execute(view::loadView);
	}
	
	public void show() {
		tabPane.setSide(Side.LEFT);
		splitPane.setDividerPositions(0.2);
		Optional.ofNullable(state).map(StoxApplicationState::getSelectedTabIndex)
			.ifPresent(tabPane.getSelectionModel()::select);
	}
	
	public void unloadView() {
		views.forEach(View::unloadView);
		
		final StoxApplicationState state = new StoxApplicationState();
		state.setSelectedTabIndex(tabPane.getSelectionModel().getSelectedIndex());
		final SerializationService serializationService = context.getSerializationService();
		serializationService.serialize(state);
	}
}
