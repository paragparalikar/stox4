package com.stox.workbench;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.HasNode;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import lombok.NonNull;

public class WorkbenchMenuBar implements HasNode<Node> {

	private final Menu newMenu;
	private final MenuBar menuBar = new MenuBar();
	private final FxMessageSource messageSource;

	public WorkbenchMenuBar(final FxMessageSource messageSource) {
		this.messageSource = messageSource;
		menuBar.getStyleClass().addAll("primary", "workbench-menu-bar");
		menuBar.getMenus().add(newMenu = buildNewMenu());
	}

	private Menu buildNewMenu() {
		final Menu newMenu = new Menu();
		newMenu.textProperty().bind(messageSource.get("New"));
		return newMenu;
	}

	private MenuItem buildMenuItem(@NonNull final String icon, @NonNull final ObservableValue<String> textValue, @NonNull final EventHandler<ActionEvent> eventHandler) {
		final MenuItem item = new MenuItem();
		item.textProperty().bind(textValue);
		item.getStyleClass().addAll("primary");
		item.setGraphic(new FluentLabel(icon).classes("primary", "icon"));
		item.setOnAction(eventHandler);
		return item;
	}

	public WorkbenchMenuBar newMenuItem(@NonNull final String icon, @NonNull final ObservableValue<String> textValue, @NonNull final EventHandler<ActionEvent> eventHandler) {
		newMenu.getItems().add(buildMenuItem(icon, textValue, eventHandler));
		return this;
	}

	@Override
	public Node getNode() {
		return menuBar;
	}

}
