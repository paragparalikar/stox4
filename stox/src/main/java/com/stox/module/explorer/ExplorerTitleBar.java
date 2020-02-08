package com.stox.module.explorer;

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

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Toggle;
import lombok.NonNull;

public class ExplorerTitleBar extends ModuleTitleBar {

	private final Toggle filterToggle;
	private final Toggle searchToggle;
	private final LinkButton linkButton = new LinkButton();
	private final FluentComboBox<Exchange> exchangeComboBox = new FluentComboBox<Exchange>().items(Exchange.values()).classes("primary", "inverted").fullWidth();

	public ExplorerTitleBar(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final EventHandler<ActionEvent> closeEventHandler,
			@NonNull final SearchableListView<Scrip> listView, @NonNull final Consumer<Exchange> consumer) {
		super(icon, titleValue, closeEventHandler);
		getTitleBar().append(Side.RIGHT, linkButton);
		filterToggle = appendToggleNode(Icon.FILTER, exchangeComboBox);
		searchToggle = appendToggleNode(Icon.SEARCH, new SearchBox<Scrip>(listView, this::test).classes("primary"));
		exchangeComboBox.selectionModel().selectedItemProperty().addListener((o, old, exchange) -> consumer.accept(exchange));
		exchangeComboBox.select(Exchange.NSE);
	}
	
	ExplorerTitleBar scrip(final Scrip scrip) {
		linkButton.getLink().setState(new State(0, scrip, null));
		return this;
	}
	
	public Exchange exchange() {
		return exchangeComboBox.getValue();
	}
	
	public ExplorerTitleBar exchange(@NonNull final Exchange exchange) {
		exchangeComboBox.setValue(exchange);
		return this;
	}
	
	public boolean searchVisible() {
		return searchToggle.isSelected();
	}
	
	public ExplorerTitleBar searchVisible(final boolean value) {
		searchToggle.setSelected(value);
		return this;
	}
	
	public boolean filterVisible() {
		return filterToggle.isSelected();
	}
	
	public ExplorerTitleBar filterVisible(final boolean value) {
		filterToggle.setSelected(value);
		return this;
	}

	public boolean test(final Scrip scrip, String text) {
		text = text.trim().toLowerCase();
		return null != scrip && (scrip.getName().trim().toLowerCase().contains(text) ||
				scrip.getCode().trim().toLowerCase().contains(text) ||
				scrip.getIsin().trim().toLowerCase().contains(text));
	}

}
