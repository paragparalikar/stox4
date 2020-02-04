package com.stox.fx.workbench;

import com.stox.Context;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Spacer;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class WorkbenchTitleBar extends TitleBar {

	private final WorkbenchMenuBar menuBar;
	private final WindowControls windowControls;

	public WorkbenchTitleBar(final Context context, final Workbench workbench) {
		super(context.getMessageSource().get("application.title", "Stox"));
		menuBar = new WorkbenchMenuBar(context, workbench);
		center(new FluentHBox(menuBar, new Spacer(), new AuthorContact())).right(windowControls = new WindowControls(context.getMessageSource()));
		addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMouseEvent);
	}

	private void onMouseEvent(MouseEvent event) {
		if (2 == event.getClickCount() && MouseButton.PRIMARY.equals(event.getButton())) {
			windowControls.getRestoreButton().fire();
		}
	}

}
