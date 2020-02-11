package com.stox.module.explorer;

import java.util.Objects;
import java.util.function.Consumer;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.SearchBox;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.workbench.link.Link.State;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.geometry.Side;
import javafx.scene.control.Toggle;
import lombok.NonNull;

public class ExplorerTitleBar extends ModuleTitleBar {

	private final Toggle searchToggle;
	private final SearchBox<Scrip> searchBox;
	private final SearchableListView<Scrip> listView;
	private final LinkButton linkButton = new LinkButton();
	private final FluentComboBox<Exchange> exchangeComboBox = new FluentComboBox<Exchange>().items(Exchange.values()).classes("primary", "inverted").fullWidth();

	public ExplorerTitleBar(@NonNull final SearchableListView<Scrip> listView, @NonNull final Consumer<Exchange> consumer) {
		this.listView = listView;
		this.searchBox = new SearchBox<Scrip>(listView, this::test);
		getTitleBar().append(Side.RIGHT, linkButton);
		getTitleBar().append(Side.BOTTOM, exchangeComboBox);
		searchToggle = appendToggleNode(Icon.SEARCH, searchBox.getNode());
		exchangeComboBox.selectionModel().selectedItemProperty().addListener((o, old, exchange) -> consumer.accept(exchange));
		exchangeComboBox.select(Exchange.NSE);
		listView.getSelectionModel().selectedItemProperty().addListener((o, old, scrip) -> linkButton.getLink().setState(new State(0, scrip, null)));
	}
	
	ExplorerViewState state() {
		final Scrip scrip = listView.getSelectionModel().getSelectedItem();
		return new ExplorerViewState()
				.isin(Objects.isNull(scrip) ? null : scrip.getIsin())
				.exchange(exchangeComboBox.getValue())
				.searchText(searchBox.text())
				.searchVisible(searchToggle.isSelected());
	}
	
	ExplorerTitleBar state(@NonNull final ExplorerViewState state) {
		exchangeComboBox.select(state.exchange());
		searchToggle.setSelected(state.searchVisible());
		searchBox.text(state.searchText());
		listView.getItems().stream()
			.filter(Objects::nonNull)
			.filter(scrip -> Objects.equals(scrip.getIsin(), state.isin()))
			.findFirst().ifPresent(scrip -> {
				listView.scrollTo(scrip);
				listView.getSelectionModel().select(scrip);
			});
		return this;
	}
	

	public boolean test(final Scrip scrip, String text) {
		text = text.trim().toLowerCase();
		return null != scrip && (scrip.getName().trim().toLowerCase().contains(text) ||
				scrip.getCode().trim().toLowerCase().contains(text) ||
				scrip.getIsin().trim().toLowerCase().contains(text));
	}

}
