package com.stox.module.charting.plot.info;


import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Icon;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SimplePlotInfoPane implements PlotInfoPane {

	private final Label nameLabel = new Label();
	private final Button removeButton = new FluentButton(Icon.TIMES).classes("icon", "primary", "inverted");
	private final Button visibilityButton = new FluentButton(Icon.EYE).classes("icon", "primary", "inverted");
	private final HBox container = new FluentHBox(removeButton, visibilityButton, nameLabel).classes("plot-info-pane");

	@Override
	public HBox getNode() {
		return container;
	}

	@Override
	public SimplePlotInfoPane setName(String name) {
		nameLabel.setText(name);
		return this;
	}

	@Override
	public SimplePlotInfoPane bind(ObjectProperty<Color> colorProperty) {
		nameLabel.textFillProperty().bind(colorProperty);
		return this;
	}

	@Override
	public SimplePlotInfoPane addRemoveEventHandler(EventHandler<ActionEvent> eventHandler) {
		removeButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		return this;
	}

	@Override
	public SimplePlotInfoPane addVisibilityEventHandler(EventHandler<ActionEvent> eventHandler) {
		visibilityButton.addEventHandler(ActionEvent.ACTION, eventHandler);
		return this;
	}

}
