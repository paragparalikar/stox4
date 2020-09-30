package com.stox.module.watchlist.widget;


import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.OrderableListCell;
import com.stox.fx.widget.Spacer;
import com.stox.module.core.model.BarSpan;
import com.stox.module.watchlist.event.WatchlistEntryDeletedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;

public class WatchlistEntryListCell extends OrderableListCell<WatchlistEntry> {

	private final Button deleteButton = new FluentButton(Icon.TRASH).onAction(event -> delete()).classes("icon", "danger", "inverted");
	private final HBox buttonsBar = new FluentHBox(deleteButton);
	private final Label nameLabel = new FluentLabel();
	private final HBox container = new FluentHBox(nameLabel, new Spacer(), buttonsBar);
	
	public WatchlistEntryListCell() {
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		setOnDragDone(this::onDragDone);
	}
	
	private void onDragDone(final DragEvent event) {
		final Watchlist watchlist = (Watchlist) getListView().getUserData();
		final ObservableList<WatchlistEntry> entries = getListView().getItems();
		final List<WatchlistEntry> indexedEntries = IntStream.range(0, entries.size())
			.mapToObj(index -> entries.get(index).index(index))
			.collect(Collectors.toList());
		final BarSpan barSpan = indexedEntries.get(0).barSpan();
		watchlist.entries().get(barSpan).sort(Comparator.naturalOrder());
		FXCollections.sort(entries);
	}
	
	@Override
	protected void updateItem(WatchlistEntry entry, boolean empty) {
		super.updateItem(entry, empty);
		if(Objects.isNull(entry) || empty) {
			setGraphic(null);
		} else {
			setGraphic(container);
			nameLabel.setText(entry.scrip().name());
		}
	}
	
	private void delete() {
		final WatchlistEntry watchlistEntry = getItem();
		if(Objects.nonNull(watchlistEntry)) {
			final Watchlist watchlist = (Watchlist) getListView().getUserData();
			watchlist.remove(watchlistEntry);
			getListView().getItems().remove(watchlistEntry);
			fireEvent(new WatchlistEntryDeletedEvent(watchlistEntry));
		}
	}
	
}
