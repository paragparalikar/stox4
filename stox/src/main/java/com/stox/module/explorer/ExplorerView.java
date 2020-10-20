package com.stox.module.explorer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.stox.fx.widget.Ui;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.event.ScripsChangedEvent;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.EventBus;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class ExplorerView extends ModuleView<ExplorerViewState> {

	private final EventBus eventBus;
	@Getter
	private final ExplorerTitleBar titleBar;
	private final ScripRepository scripRepository;
	private final SearchableListView<Scrip> listView = new SearchableListView<>();
	private final Consumer<ScripsChangedEvent> scripsChangedHandler = this::onScripsChanged;

	public ExplorerView(@NonNull final EventBus eventBus, @NonNull final ScripRepository scripRepository) {
		this.eventBus = eventBus;
		this.scripRepository = scripRepository;
		content(listView);
		title(titleBar = new ExplorerTitleBar(listView, this::load));
	}

	private void load(@NonNull final Exchange exchange) {
		listView.getItems().setAll(scripRepository.find(exchange));
	}
	
	private void onScripsChanged(final ScripsChangedEvent event) {
		if(Objects.equals(titleBar.exchange(), event.exchange())) {
			final List<Scrip> scrips = event.scrips().stream().sorted().collect(Collectors.toList());
			Ui.fx(() -> listView.getItems().setAll(scrips));
		}
	}
	
	@Override
	public ExplorerView start(ExplorerViewState state, Bounds bounds) {
		super.start(state, bounds);
		eventBus.subscribe(ScripsChangedEvent.class, scripsChangedHandler);
		load(titleBar.state(state).bind().exchange());
		Optional.ofNullable(state).ifPresent(value -> titleBar.select(scripRepository.find(state.isin())));
		return this;
	}
	
	@Override
	public ExplorerViewState stop(Bounds bounds) {
		eventBus.unsubscribe(ScripsChangedEvent.class, scripsChangedHandler);
		return stop(titleBar.state(), bounds);
	}
	
}
