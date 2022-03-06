package com.stox.watchlist;

import java.util.Optional;

import com.stox.common.ui.DefaultDialogx;
import com.stox.common.ui.Icon;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class WatchlistControlsMenuButton extends MenuButton {

	private final WatchlistService watchlistService;
	private final WatchlistComboBox watchlistComboBox;
	private final MenuItem createMenuItem = new MenuItem("New");
	private final MenuItem deleteMenuItem = new MenuItem("Delete");
	private final MenuItem truncateMenuItem = new MenuItem("Truncate");
	
	public WatchlistControlsMenuButton(WatchlistComboBox watchlistComboBox, WatchlistService watchlistService) {
		this.watchlistService = watchlistService;
		this.watchlistComboBox = watchlistComboBox;
		getItems().addAll(createMenuItem, truncateMenuItem, deleteMenuItem);
		style();
		bind();
	}
	
	private void style() {
		final Label gearIcon = new Label(Icon.GEAR);
		gearIcon.getStyleClass().add("icon");
		setGraphic(gearIcon);
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		final Label createIcon = new Label(Icon.PLUS);
		createIcon.getStyleClass().addAll("icon");
		createMenuItem.setGraphic(createIcon);
		createMenuItem.getStyleClass().add("first");
		final Label truncateIcon = new Label(Icon.ERASER);
		truncateIcon.getStyleClass().addAll("icon");
		truncateMenuItem.setGraphic(truncateIcon);
		final Label deleteIcon = new Label(Icon.TRASH);
		deleteIcon.getStyleClass().add("icon");
		deleteMenuItem.setGraphic(deleteIcon);
		deleteMenuItem.getStyleClass().add("last");
	}

	private void bind() {
		createMenuItem.setOnAction(event -> create());
		deleteMenuItem.setOnAction(event -> delete());
		truncateMenuItem.setOnAction(event -> truncate());
	}
	
	private void create() {
		final TextField textField = new TextField();
		new DefaultDialogx()
			.withTitle("Create New Watchlist")
			.withContent(textField)
			.withButton(ButtonType.APPLY, () -> {
				final Watchlist watchlist = new Watchlist();
				watchlist.setName(textField.getText());
				watchlistService.create(watchlist);
			}).withButton(ButtonType.CANCEL);
	}
	
	private void delete() {
		Optional.ofNullable(watchlistComboBox.getValue()).ifPresent(watchlist -> {
			watchlistService.delete(watchlist.getName());
		});
	}
	
	private void truncate() {
		
	}
}
