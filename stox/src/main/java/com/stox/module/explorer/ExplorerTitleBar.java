package com.stox.module.explorer;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.SearchBox;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.NonNull;

public class ExplorerTitleBar extends ModuleTitleBar {

	private final LinkButton linkButton = new LinkButton();
	private final FluentComboBox<Exchange> exchangeComboBox = new FluentComboBox<Exchange>().items(Exchange.values()).select(Exchange.NSE).classes("primary", "inverted")
			.fullWidth();

	public ExplorerTitleBar(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final EventHandler<ActionEvent> closeEventHandler,
			@NonNull final SearchableListView<Scrip> listView) {
		super(icon, titleValue, closeEventHandler);
		appendToggleNode(Icon.FILTER, exchangeComboBox);
		appendToggleNode(Icon.SEARCH, new SearchBox<Scrip>(listView, this::test).classes("primary"));
	}

	public boolean test(final Scrip scrip, String text) {
		text = text.trim().toLowerCase();
		return null != scrip && (scrip.getName().trim().toLowerCase().contains(text) ||
				scrip.getCode().trim().toLowerCase().contains(text) ||
				scrip.getIsin().trim().toLowerCase().contains(text));
	}

}
