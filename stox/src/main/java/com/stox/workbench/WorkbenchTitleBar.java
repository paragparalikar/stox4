package com.stox.workbench;

import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Spacer;
import com.stox.fx.widget.TitleBar;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.Getter;

public class WorkbenchTitleBar extends TitleBar {

	@Getter
	private final WorkbenchMenuBar menuBar;
	private final WindowControls windowControls;
	
	public WorkbenchTitleBar(final FxMessageSource messageSource) {
		this.windowControls = new WindowControls(messageSource);
		this.menuBar = new WorkbenchMenuBar(messageSource);
		
		center(new FluentHBox(menuBar, new Spacer(), new AuthorContact())).right(windowControls);
		addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMouseEvent);
	}

	private void onMouseEvent(MouseEvent event) {
		if (2 == event.getClickCount() && MouseButton.PRIMARY.equals(event.getButton())) {
			windowControls.getRestoreButton().fire();
		}
	}

}
