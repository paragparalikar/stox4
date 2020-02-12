package com.stox.module.explorer;

import java.util.Objects;

import com.stox.fx.widget.Ui;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.event.ScripsChangedEvent;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.workbench.module.ModuleView;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class ExplorerView extends ModuleView<ExplorerViewState> {

	@Getter
	private final ExplorerTitleBar titleBar;
	private final ScripRepository scripRepository;
	private final SearchableListView<Scrip> listView = new SearchableListView<>();
	private final EventHandler<ScripsChangedEvent> scripsChangedHandler = this::onScripsChanged;

	public ExplorerView(@NonNull final ScripRepository scripRepository) {
		this.scripRepository = scripRepository;
		content(listView);
		title(titleBar = new ExplorerTitleBar(listView, this::load));
	}

	private void load(@NonNull final Exchange exchange) {
		listView.getItems().setAll(scripRepository.find(exchange));
	}
	
	private void onScripsChanged(final ScripsChangedEvent event) {
		if(Objects.equals(titleBar.exchange(), event.exchange())) {
			Ui.fx(() -> listView.getItems().setAll(event.scrips()));
		}
	}
	
	@Override
	public ExplorerView start(ExplorerViewState state, Bounds bounds) {
		super.start(state, bounds);
		titleBar.state(state);
		getNode().getScene().getRoot().addEventHandler(ScripsChangedEvent.TYPE, scripsChangedHandler);
		return this;
	}
	
	@Override
	public ExplorerViewState stop(Bounds bounds) {
		getNode().getScene().getRoot().removeEventHandler(ScripsChangedEvent.TYPE, scripsChangedHandler);
		return stop(titleBar.state(), bounds);
	}
	
}
