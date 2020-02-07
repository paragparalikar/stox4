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
		listView.getItems().addAll(context.getScripRepository().find(Exchange.NSE));
		title(titleBar = new ExplorerTitleBar(icon, titleValue, super::onClose, listView));
	}

}
