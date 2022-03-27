package com.stox.watchlist;

import java.util.Optional;

import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import lombok.Builder;

@Builder
public class WatchlistEntryCell extends ListCell<String> {
	
	private final ScripService scripService;
	private final WatchlistService watchlistService;
	private final ComboBox<Watchlist> watchlistComboBox;
	private final Button deleteButton = new Button(Icon.TRASH);
	
	public WatchlistEntryCell(
			ScripService scripService, 
			WatchlistService watchlistService,
			ComboBox<Watchlist> watchlistComboBox) {
		this.scripService = scripService;
		this.watchlistService = watchlistService;
		this.watchlistComboBox = watchlistComboBox;
		deleteButton.getStyleClass().setAll("icon", "button");
		
		setOnDragDetected(this::onDragDetected);
		setOnMouseDragEntered(this::onMouseDragEntered);
		setOnMouseDragOver(this::onMouseDragOver);
		setOnMouseDragExited(this::onMouseDragExisted);
		setOnMouseDragReleased(getOnDragDetected());
		setOnMouseDragReleased(this::onMouseDragReleased);
	}
	
	private void onDragDetected(MouseEvent event) {
		if(null != getItem()) startFullDrag();
	}
	
	private void onMouseDragOver(MouseDragEvent event) {
		final Object gestureSource = event.getGestureSource();
		if(null != gestureSource && WatchlistEntryCell.this != gestureSource) {
			pseudoClassStateChanged(Fx.PSEUDO_CLASS_DRAGOVER, true);
		}
	}
	
	private void onMouseDragEntered(MouseDragEvent event) {
		final Object gestureSource = event.getGestureSource();
		if(null != gestureSource && WatchlistEntryCell.this != gestureSource) {
			pseudoClassStateChanged(Fx.PSEUDO_CLASS_DRAGOVER, true);
		}
	}

	private void onMouseDragExisted(MouseDragEvent event) {
		final Object gestureSource = event.getGestureSource();
		if(null != gestureSource && WatchlistEntryCell.this != gestureSource) {
			pseudoClassStateChanged(Fx.PSEUDO_CLASS_DRAGOVER, false);
		}
	}
	
	private void onMouseDragReleased(MouseDragEvent event) {
		final Object gestureSource = event.getGestureSource();
		if(null != gestureSource && gestureSource instanceof ListCell) {
			final ListCell<String> cell = ListCell.class.cast(gestureSource);
			final String isin = cell.getItem();
			final Watchlist watchlist = watchlistComboBox.getValue();
			if(null != isin && null != watchlist) {
				final int currentIndex = getListView().getItems().indexOf(getItem());
				watchlist.getEntries().remove(isin);
				watchlist.getEntries().add(currentIndex, isin);
				watchlistService.update(watchlist);
			}
		}
	}
	
	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if(empty || null == item || 0 == item.trim().length()) {
			setText(null);
			setGraphic(null);
		} else {
			final Scrip scrip = scripService.findByIsin(item);
			setText(null == scrip ? item : scrip.getName());
			setGraphic(deleteButton);
			deleteButton.setOnAction(event -> remove(item));
		}
	}
	
	private void remove(String isin) {
		Optional.ofNullable(watchlistComboBox.getValue()).ifPresent(watchlist -> {
			watchlistService.removeEntry(watchlist.getName(), isin);
		});
	}
}
