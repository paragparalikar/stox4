package com.stox.module.explorer;

import java.util.function.Consumer;

import com.google.gson.Gson;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.StringUtil;
import com.stox.workbench.module.ModuleView;
import com.stox.workbench.module.ModuleViewState;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class ExplorerView extends ModuleView {

	private final Gson gson;
	@Getter
	private final ExplorerTitleBar titleBar;
	private final ScripRepository scripRepository;
	private final SearchableListView<Scrip> listView = new SearchableListView<>();

	@Builder
	public ExplorerView(
			@NonNull final Gson gson,
			@NonNull final String icon, 
			@NonNull final ScripRepository scripRepository,
			@NonNull final ObservableValue<String> titleValue,
			@NonNull final Consumer<ModuleView> closeConsumer) {
		content(listView);
		this.gson = gson;
		this.scripRepository = scripRepository;
		title(titleBar = ExplorerTitleBar.builder()
				.icon(icon)
				.titleValue(titleValue)
				.closeEventHandler(event -> closeConsumer.accept(this))
				.listView(listView)
				.consumer(this::load)
				.build());
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
