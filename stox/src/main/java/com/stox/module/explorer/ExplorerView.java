package com.stox.module.explorer;

import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.workbench.module.ModuleView;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class ExplorerView extends ModuleView<ExplorerViewState> {

	@Getter
	private final ExplorerTitleBar titleBar;
	private final ScripRepository scripRepository;
	private final SearchableListView<Scrip> listView = new SearchableListView<>();

	public ExplorerView(@NonNull final ScripRepository scripRepository) {
		this.scripRepository = scripRepository;
		
		content(listView);
		title(titleBar = new ExplorerTitleBar(listView, this::load));
	}

	private void load(@NonNull final Exchange exchange) {
		listView.getItems().setAll(scripRepository.find(exchange));
	}
	
	@Override
	public ExplorerView start(ExplorerViewState state, Bounds bounds) {
		super.start(state, bounds);
		titleBar.state(state);
		return this;
	}
	
	@Override
	public ExplorerViewState stop(Bounds bounds) {
		return stop(titleBar.state(), bounds);
	}
	
}
