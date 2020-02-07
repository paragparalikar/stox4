package com.stox.module.explorer;

import com.stox.Context;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.workbench.module.ModuleView;

import javafx.beans.value.ObservableValue;
import lombok.Getter;
import lombok.NonNull;

public class ExplorerView extends ModuleView {

	@Getter
	private final ExplorerTitleBar titleBar;
	private final SearchableListView<Scrip> listView = new SearchableListView<>();

	public ExplorerView(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final Context context) {
		super(context);
		content(listView);
		title(titleBar = new ExplorerTitleBar(icon, titleValue, super::onClose, listView, this::load));
		listView.getSelectionModel().selectedItemProperty().addListener((o,old,scrip) -> titleBar.scrip(scrip));
	}

	private void load(@NonNull final Exchange exchange) {
		listView.getItems().addAll(getContext().getScripRepository().find(exchange));
	}
}
