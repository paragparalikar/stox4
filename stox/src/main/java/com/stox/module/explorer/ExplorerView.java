package com.stox.module.explorer;

import com.stox.Context;
import com.stox.fx.fluent.scene.control.FluentComboBox;
import com.stox.fx.fluent.scene.control.FluentToggleButton;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;
import com.stox.fx.widget.search.SearchBox;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.module.ModuleView;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import lombok.NonNull;

public class ExplorerView extends ModuleView<ExplorerView> {
	
	private final SearchableListView<Scrip> listView = new SearchableListView<>();
	//private final FluentToggleButton filterButton = new FluentToggleButton(Icon.FILTER).classes("primary","icon").onAction(e -> toggleFilter()));
	private final FluentComboBox<Exchange> exchangeComboBox = new FluentComboBox<Exchange>().items(Exchange.values()).select(Exchange.NSE).classes("primary","inverted").fullWidth();
	//private final FluentToggleButton searchButton = new FluentToggleButton(Icon.SEARCH).classes("primary","icon").onAction(e -> toggleSearch());
	private final SearchBox<Scrip> searchBox = new SearchBox<Scrip>(listView, this::test);
	private final LinkButton linkButton = new LinkButton();

	public ExplorerView(@NonNull final String icon,@NonNull final ObservableValue<String> titleValue,@NonNull final Context context) {
		super(icon, titleValue, context);
		listView.getItems().addAll(context.getScripRepository().find(Exchange.NSE));
		content(listView);
	}
	
	
	public boolean test(final Scrip scrip, String text) {
		text = text.trim().toLowerCase();
		return null != scrip && (
				scrip.getName().trim().toLowerCase().contains(text) || 
				scrip.getCode().trim().toLowerCase().contains(text) ||
				scrip.getIsin().trim().toLowerCase().contains(text));
	}
	
	private void toggleFilter(){
		//getTitleBar().add(filterButton.isSelected(), Side.BOTTOM, exchangeComboBox);
	}

	@Override
	public ExplorerView getThis() {
		return this;
	}

}
