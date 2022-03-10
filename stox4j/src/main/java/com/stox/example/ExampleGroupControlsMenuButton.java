package com.stox.example;

import java.util.Optional;

import com.stox.common.ui.Fx;
import com.stox.common.ui.Icon;
import com.stox.common.ui.modal.ConfirmationModal;
import com.stox.common.ui.modal.Modal;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class ExampleGroupControlsMenuButton extends MenuButton {

	private final ExampleService exampleService;
	private final ExampleGroupService exampleGroupService;
	private final ExampleGroupComboBox exampleGroupComboBox;
	private final MenuItem createMenuItem = new MenuItem("New");
	private final MenuItem renameMenuItem = new MenuItem("Rename");
	private final MenuItem deleteMenuItem = new MenuItem("Delete");
	private final MenuItem clearMenuItem = new MenuItem("Clear");
	
	public ExampleGroupControlsMenuButton(
			ExampleService exampleService,
			ExampleGroupService exampleGroupService, 
			ExampleGroupComboBox exampleGroupComboBox) {
		this.exampleService = exampleService;
		this.exampleGroupService = exampleGroupService;
		this.exampleGroupComboBox = exampleGroupComboBox;
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
		textField.setPromptText("Example Group Name");
		final Label graphics = new Label(Icon.PLUS);
		graphics.getStyleClass().add("icon");
		final Button button = new Button("Create", graphics);
		final Modal modal = new Modal()
			.withTitleIcon(Icon.BOOKMARK)
			.withTitleText("Create New Example Group")
			.withContent(textField)
			.withButton(button)
			.show(this);
		button.setOnAction(event -> create(textField.getText(), modal));
		button.setDefaultButton(true);
		Fx.run(textField::requestFocus);
	}
	
	private void create(String name, Modal modal) {
		final ExampleGroup exampleGroup = new ExampleGroup(null, name);
		exampleGroupService.create(exampleGroup);
		modal.hide();
	}
	
	private void rename() {
		Optional.ofNullable(exampleGroupComboBox.getValue()).ifPresent(group -> {
			final String oldName = group.getName();
			final TextField textField = new TextField();
			textField.setMaxWidth(Double.MAX_VALUE);
			textField.getStyleClass().add("large");
			textField.setText(oldName);
			final Label graphics = new Label(Icon.EDIT);
			graphics.getStyleClass().add("icon");
			final Button button = new Button("Rename", graphics);
			final Modal modal = new Modal()
				.withTitleIcon(Icon.BOOKMARK)
				.withTitleText("Rename Example Group")
				.withContent(textField)
				.withButton(button)
				.show(this);
			button.setOnAction(event -> rename(group, textField.getText(), modal));
			button.setDefaultButton(true);
			Fx.run(textField::requestFocus);
			textField.positionCaret(oldName.length());
		});
	}
	
	private void rename(ExampleGroup exampleGroup, String newName, Modal modal) {
		final String oldName = exampleGroup.getName();
		if(null != newName && 0 < newName.trim().length() && !newName.equalsIgnoreCase(oldName)) {
			exampleGroup.setName(newName);
			exampleGroupService.update(exampleGroup);
			modal.hide();
		}
	}
	
	private void delete() {
		Optional.ofNullable(exampleGroupComboBox.getValue()).ifPresent(group -> {
			new ConfirmationModal()
				.withMessageIcon(Icon.WARNING)
				.withMessageText("Are you sure you want to delete example group \"" + group.getName() + "\"")
				.withAction(() -> exampleGroupService.deleteById(group.getId()))
				.withTitleIcon(Icon.BOOKMARK)
				.withTitleText("Delete example group \"" + group.getName() + "\"")
				.show(this);
		});
	}
	
	private void clear() {
		Optional.ofNullable(exampleGroupComboBox.getValue()).ifPresent(group -> {
			new ConfirmationModal()
			.withMessageIcon(Icon.WARNING)
			.withMessageText("Are you sure you want to clear example group \"" + group.getName() + "\"")
			.withAction(() -> exampleService.clear(group.getId()))
			.withTitleIcon(Icon.BOOKMARK)
			.withTitleText("Clear example group \"" + group.getName() + "\"")
			.show(this);
		});
	}
}
