package com.stox.watchlist;

import java.util.Optional;

import com.stox.common.ui.DefaultDialogx;
import com.stox.common.ui.Icon;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class WatchlistButtonBar extends HBox {

	private final WatchlistService watchlistService;
	private final WatchlistComboBox watchlistComboBox;
	private final Button createButton = new Button(Icon.PLUS);
	private final Button deleteButton = new Button(Icon.TRASH);
	private final Button truncateButton = new Button(Icon.ERASER);
	
	public WatchlistButtonBar(WatchlistComboBox watchlistComboBox, WatchlistService watchlistService) {
		this.watchlistService = watchlistService;
		this.watchlistComboBox = watchlistComboBox;
		style();
		bind();
	}
	
	private void style() {
		getStyleClass().add("button-bar");
		createButton.getStyleClass().add("icon");
		deleteButton.getStyleClass().add("icon");
		truncateButton.getStyleClass().add("icon");
	}

	private void bind() {
		createButton.setOnAction(event -> create());
		deleteButton.setOnAction(event -> delete());
		truncateButton.setOnAction(event -> truncate());
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
			}).withButton(ButtonType.CANCEL)
			.show(this);
	}
	
	private void delete() {
		Optional.ofNullable(watchlistComboBox.getValue()).ifPresent(watchlist -> {
			watchlistService.delete(watchlist.getName());
		});
	}
	
	private void truncate() {
		
	}
}
