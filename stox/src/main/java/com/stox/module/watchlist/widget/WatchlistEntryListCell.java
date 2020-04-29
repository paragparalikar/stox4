package com.stox.module.watchlist.widget;


import java.util.Objects;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Spacer;
import com.stox.module.watchlist.event.WatchlistEntryDeletedEvent;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.module.watchlist.repository.WatchlistEntryRepository;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import lombok.NonNull;

public class WatchlistEntryListCell extends ListCell<WatchlistEntry> {

	private final Button deleteButton = new FluentButton(Icon.TRASH).onAction(event -> delete()).classes("icon", "danger", "inverted");
	private final HBox buttonsBar = new FluentHBox(deleteButton);
	private final Label nameLabel = new FluentLabel();
	private final HBox container = new FluentHBox(nameLabel, new Spacer(), buttonsBar);
	
	private final WatchlistEntryRepository watchlistEntryRepository;
	
	public WatchlistEntryListCell(@NonNull final WatchlistEntryRepository watchlistEntryRepository) {
		this.watchlistEntryRepository = watchlistEntryRepository;
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}
	
	@Override
	protected void updateItem(WatchlistEntry entry, boolean empty) {
		super.updateItem(entry, empty);
		if(Objects.isNull(entry) || empty) {
			setGraphic(null);
		} else {
			setGraphic(container);
			nameLabel.setText(entry.getScrip().getName());
		}
	}
	
	private void delete() {
		final WatchlistEntry watchlistEntry = getItem();
		if(Objects.nonNull(watchlistEntry)) {
			watchlistEntryRepository.delete(watchlistEntry.getId());
			getListView().getItems().remove(watchlistEntry);
			fireEvent(new WatchlistEntryDeletedEvent(watchlistEntry));
		}
	}
	
}
