package com.stox.module.explorer;

import java.util.Objects;
import java.util.function.Consumer;

import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.Scroller;
import com.stox.fx.widget.search.SearchBox;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.fx.widget.search.Selector;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.CoreConstant;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.geometry.Side;
import javafx.scene.control.Toggle;
import lombok.NonNull;

public class ExplorerTitleBar extends ModuleTitleBar {

	private final Toggle searchToggle;
	private final SearchBox<Scrip> searchBox;
	private final SearchableListView<Scrip> listView;
	private final LinkButton linkButton = new LinkButton();
	private final Consumer<Exchange> exchangeSelectionConsumer;
	private final FluentComboBox<Exchange> exchangeComboBox = new FluentComboBox<Exchange>().items(Exchange.values()).classes("primary", "inverted").fullWidth();

	public ExplorerTitleBar(@NonNull final SearchableListView<Scrip> listView, @NonNull final Consumer<Exchange> consumer) {
		this.listView = listView;
		this.exchangeSelectionConsumer = consumer;
		this.searchBox = new SearchBox<Scrip>(listView, this::test);
		getTitleBar().append(Side.RIGHT, linkButton);
		getTitleBar().append(Side.BOTTOM, exchangeComboBox);
		searchToggle = appendToggleNode(Icon.SEARCH, searchBox.getNode());
	}
	
	ExplorerTitleBar select(final Scrip scrip) {
		Selector.of(scrip).select(listView.getSelectionModel());
		Scroller.of(scrip).scroll(listView);
		return this;
	}

	Exchange exchange() {
		return exchangeComboBox.value();
	}

	ExplorerTitleBar bind() {
		exchangeComboBox.selectionModel().selectedItemProperty().addListener((o, old, exchange) -> exchangeSelectionConsumer.accept(exchange));
		listView.getSelectionModel().selectedItemProperty().addListener(
				(o, old, scrip) -> linkButton.getLink().setState(LinkState.builder()
						.put(CoreConstant.KEY_TO, String.valueOf(0))
						.put(CoreConstant.KEY_ISIN, null == scrip ? null : scrip.isin())
						.build()));
		return this;
	}
	
	ExplorerViewState state() {
		final Scrip scrip = listView.getSelectionModel().getSelectedItem();
		return new ExplorerViewState()
				.isin(Objects.isNull(scrip) ? null : scrip.isin())
				.exchange(exchangeComboBox.getValue())
				.searchText(searchBox.text())
				.searchVisible(searchToggle.isSelected());
	}

	ExplorerTitleBar state(final ExplorerViewState state) {
		if (Objects.isNull(state)) {
			exchangeComboBox.select(Exchange.NSE);
		} else {
			exchangeComboBox.select(state.exchange());
			searchToggle.setSelected(state.searchVisible());
			searchBox.text(state.searchText());
		}
		return this;
	}

	public boolean test(final Scrip scrip, String text) {
		text = text.trim().toLowerCase();
		return null != scrip && (scrip.name().trim().toLowerCase().contains(text) ||
				scrip.code().trim().toLowerCase().contains(text) ||
				scrip.isin().trim().toLowerCase().contains(text));
	}

}
