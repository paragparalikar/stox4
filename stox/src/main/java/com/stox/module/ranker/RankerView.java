package com.stox.module.ranker;

import com.stox.fx.fluent.scene.control.FluentProgressBar;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Scrip;
import com.stox.module.ranker.modal.RankerConfigChangedEvent;
import com.stox.workbench.module.ModuleView;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class RankerView extends ModuleView<RankerViewState> implements EventHandler<RankerConfigChangedEvent> {
	
	private final RankerService rankerService;
	@Getter private final RankerTitleBar titleBar;
	private final RankerConfig rankerConfig = new RankerConfig();
	private final FluentProgressBar progressBar = new FluentProgressBar().fullWidth().classes("primary", "success");
	private final SearchableListView<Scrip> listView = new SearchableListView<>();

	public RankerView(
			@NonNull final RankerService rankerService,
			@NonNull final FxMessageSource messageSource) {
		this.rankerService = rankerService;
		titleBar = new RankerTitleBar(rankerConfig, messageSource);
		title(titleBar).content(listView).tool(progressBar);
		getNode().addEventHandler(RankerConfigChangedEvent.TYPE, this);
		progressBar.progressProperty().bind(rankerService.progressProperty());
	}
	
	@Override
	public void handle(RankerConfigChangedEvent event) {
		rankerService.setOnSucceeded(e -> {
			listView.getItems().setAll(rankerService.getScrips());
		});
		rankerService.start();
	}
	
	@Override
	public RankerViewState stop(Bounds bounds) {
		return super.stop(new RankerViewState(), bounds);
	}

}
