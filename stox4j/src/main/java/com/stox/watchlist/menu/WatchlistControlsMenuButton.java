package com.stox.watchlist.menu;

import java.util.Optional;

import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.ConfirmationModal;
import com.stox.common.ui.modal.Modal;
import com.stox.watchlist.Watchlist;
import com.stox.watchlist.WatchlistComboBox;
import com.stox.watchlist.WatchlistService;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class WatchlistControlsMenuButton extends MenuButton {

	private final WatchlistService watchlistService;
	private final WatchlistComboBox watchlistComboBox;
	private final MenuItem createMenuItem = new MenuItem("New");
	private final MenuItem renameMenuItem = new MenuItem("Rename");
	private final MenuItem deleteMenuItem = new MenuItem("Delete");
	private final MenuItem clearMenuItem = new MenuItem("Clear");
	
	public WatchlistControlsMenuButton(WatchlistComboBox watchlistComboBox, WatchlistService watchlistService) {
		this.watchlistService = watchlistService;
		this.watchlistComboBox = watchlistComboBox;
		getItems().addAll(createMenuItem, renameMenuItem, clearMenuItem, deleteMenuItem);
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
		final Label renameIcon = new Label(Icon.EDIT);
		renameIcon.getStyleClass().add("icon");
		renameMenuItem.setGraphic(renameIcon);
		final Label truncateIcon = new Label(Icon.ERASER);
		truncateIcon.getStyleClass().addAll("icon");
		clearMenuItem.setGraphic(truncateIcon);
		final Label deleteIcon = new Label(Icon.TRASH);
		deleteIcon.getStyleClass().add("icon");
		deleteMenuItem.setGraphic(deleteIcon);
		deleteMenuItem.getStyleClass().add("last");
	}

	private void bind() {
		createMenuItem.setOnAction(event -> create());
		renameMenuItem.setOnAction(event -> rename());
		deleteMenuItem.setOnAction(event -> delete());
		clearMenuItem.setOnAction(event -> clear());
	}
	
	private void create() {
		final TextField textField = new TextField();
		textField.setMaxWidth(Double.MAX_VALUE);
		textField.getStyleClass().add("large");
		textField.setPromptText("Watchlist Name");
		final Label graphics = new Label(Icon.PLUS);
		graphics.getStyleClass().add("icon");
		final Button button = new Button("Create", graphics);
		final Modal modal = new Modal()
			.withTitleIcon(Icon.BOOKMARK)
			.withTitleText("Create New Watchlist")
			.withContent(textField)
			.withButton(button)
			.show(this);
		button.setOnAction(event -> create(textField.getText(), modal));
		button.setDefaultButton(true);
		Fx.run(textField::requestFocus);
	}
	
	private void rename() {
		Optional.ofNullable(watchlistComboBox.getValue()).ifPresent(watchlist -> {
			final String oldName = watchlist.getName();
			final TextField textField = new TextField();
			textField.setMaxWidth(Double.MAX_VALUE);
			textField.getStyleClass().add("large");
			textField.setText(oldName);
			final Label graphics = new Label(Icon.EDIT);
			graphics.getStyleClass().add("icon");
			final Button button = new Button("Rename", graphics);
			final Modal modal = new Modal()
				.withTitleIcon(Icon.BOOKMARK)
				.withTitleText("Rename Watchlist")
				.withContent(textField)
				.withButton(button)
				.show(this);
			button.setOnAction(event -> rename(watchlist, textField.getText(), modal));
			button.setDefaultButton(true);
			Fx.run(textField::requestFocus);
			textField.positionCaret(oldName.length());
		});
	}
	
	private void rename(Watchlist watchlist, String newName, Modal modal) {
		final String oldName = watchlist.getName();
		if(null != newName && 0 < newName.trim().length() && !newName.equalsIgnoreCase(oldName)) {
			watchlistService.rename(oldName, newName);
			watchlist.setName(newName);
			modal.hide();
		}
	}
	
	private void create(String name, Modal modal) {
		watchlistService.create(new Watchlist(name));
		modal.hide();
	}
	
	private void delete() {
		Optional.ofNullable(watchlistComboBox.getValue()).ifPresent(watchlist -> {
			new ConfirmationModal()
				.withMessageIcon(Icon.WARNING)
				.withMessageText("Are you sure you want to delete watchlist \"" + watchlist.getName() + "\"")
				.withAction(() -> delete(watchlist))
				.withTitleIcon(Icon.BOOKMARK)
				.withTitleText("Delete watchlist \"" + watchlist.getName() + "\"")
				.show(this);
		});
	}
	
	private void delete(Watchlist watchlist) {
		watchlistService.delete(watchlist.getName());
		if(!watchlistComboBox.getItems().isEmpty()) {
			watchlistComboBox.getSelectionModel().select(0);
		}
	}
	
	private void clear() {
		Optional.ofNullable(watchlistComboBox.getValue()).ifPresent(watchlist -> {
			new ConfirmationModal()
			.withMessageIcon(Icon.WARNING)
			.withMessageText("Are you sure you want to clear watchlist \"" + watchlist.getName() + "\"")
			.withAction(() -> clear(watchlist))
			.withTitleIcon(Icon.BOOKMARK)
			.withTitleText("Clear watchlist \"" + watchlist.getName() + "\"")
			.show(this);
		});
	}
	
	private void clear(Watchlist watchlist) {
		watchlistService.clear(watchlist.getName());
		watchlist.getEntries().clear();
	}
}