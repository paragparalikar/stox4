package com.stox.module.charting.plot.info;


import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.Icon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import lombok.experimental.Delegate;

public class EditablePlotInfoPane implements PlotInfoPane {

	@Delegate
	private final PlotInfoPane delegate;
	private final Button editButton = new FluentButton(Icon.PENCIL).classes("icon", "primary", "inverted");

	public EditablePlotInfoPane(final PlotInfoPane delegate) {
		this.delegate = delegate;
		delegate.getNode().getChildren().add(2, editButton);
	}

	public EditablePlotInfoPane addEditEventHandler(final EventHandler<ActionEvent> eventHandler) {
		editButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		return this;
	}

}
