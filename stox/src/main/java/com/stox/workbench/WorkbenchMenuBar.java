package com.stox.workbench;

import com.stox.fx.fluent.scene.control.IFluentMenuBar;
import com.stox.fx.widget.FxMessageSource;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import lombok.Getter;

public class WorkbenchMenuBar extends MenuBar implements IFluentMenuBar<WorkbenchMenuBar>{

	@Getter
	private final Menu newMenu;
	private final FxMessageSource messageSource;
	
	public WorkbenchMenuBar(final FxMessageSource messageSource) {
		this.messageSource = messageSource;
		getStyleClass().addAll("primary", "workbench-menu-bar");
		getMenus().add(newMenu = buildNewMenu());
	}
	
	private Menu buildNewMenu() {
		final Menu newMenu = new Menu();
		newMenu.textProperty().bind(messageSource.get("New"));
		return newMenu;
	}
	
	@Override
	public WorkbenchMenuBar getThis() {
		return this;
	}
	
}
