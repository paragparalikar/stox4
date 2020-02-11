package com.stox.module.explorer;

import com.google.gson.Gson;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.StringUtil;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.ModuleViewState;

import javafx.geometry.Bounds;
import lombok.Getter;
import lombok.NonNull;

public class ExplorerView extends ModuleView {

	private final Gson gson;
	@Getter
	private final ExplorerTitleBar titleBar;
	private final ScripRepository scripRepository;
	private final SearchableListView<Scrip> listView = new SearchableListView<>();

	public ExplorerView(@NonNull final Gson gson, @NonNull final ScripRepository scripRepository) {
		this.gson = gson;
		this.scripRepository = scripRepository;
		
		content(listView);
		title(titleBar = new ExplorerTitleBar(listView, this::load));
	}

	private void load(@NonNull final Exchange exchange) {
		listView.getItems().setAll(scripRepository.find(exchange));
	}
	
	@Override
	public ModuleViewState state(@NonNull final Bounds bounds) {
		return super.state(bounds).state(gson.toJson(titleBar.state()));
	}
	
	@Override
	public ModuleView state(@NonNull final ModuleViewState state, @NonNull final Bounds bounds) {
		super.state(state, bounds);
		if(StringUtil.hasText(state.state())) {
			titleBar.state(gson.fromJson(state.state(), ExplorerViewState.class));
		}
		return this;
	}
	
}
