package com.stox.module.explorer;

import java.util.function.Consumer;

import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.workbench.module.ModuleView;

import javafx.beans.value.ObservableValue;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class ExplorerView extends ModuleView {

	@Getter
	private final ExplorerTitleBar titleBar;
	private final ScripRepository scripRepository;
	private final SearchableListView<Scrip> listView = new SearchableListView<>();

	@Builder
	public ExplorerView(@NonNull final String icon, 
			@NonNull final ObservableValue<String> titleValue,
			@NonNull final ScripRepository scripRepository,
			@NonNull final Consumer<ModuleView> closeConsumer) {
		this.scripRepository = scripRepository;
		content(listView);
		title(titleBar = new ExplorerTitleBar(icon, titleValue, event -> closeConsumer.accept(this), listView, this::load));
		listView.getSelectionModel().selectedItemProperty().addListener((o, old, scrip) -> titleBar.scrip(scrip));
	}

	private void load(@NonNull final Exchange exchange) {
		listView.getItems().addAll(scripRepository.find(exchange));
	}
	
}
