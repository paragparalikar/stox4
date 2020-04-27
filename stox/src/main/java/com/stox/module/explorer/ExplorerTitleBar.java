package com.stox.module.explorer;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
		linkButton.add(this::linkState);
	}
	
	private void linkState(final LinkState state) {
		Optional.ofNullable(state)
		.map(value -> state.get(CoreConstant.KEY_ISIN))
		.flatMap(this::find)
		.filter(Predicate.isEqual(listView.getSelectionModel().getSelectedItem()).negate())
		.ifPresent(listView.getSelectionModel()::select);
	}
	
	private Optional<Scrip> find(final String isin) {
		return listView.getItems().stream()
				.filter(scrip -> Objects.equals(scrip.getIsin(), isin))
				.findFirst();
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
						.put(CoreConstant.KEY_ISIN, null == scrip ? null : scrip.getIsin())
						.build()));
		return this;
	}
	
	ExplorerViewState state() {
		final Scrip scrip = listView.getSelectionModel().getSelectedItem();
		return new ExplorerViewState()
				.isin(Objects.isNull(scrip) ? null : scrip.getIsin())
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
		return null != scrip && (scrip.getName().trim().toLowerCase().contains(text) ||
				scrip.getCode().trim().toLowerCase().contains(text) ||
				scrip.getIsin().trim().toLowerCase().contains(text));
	}

}
