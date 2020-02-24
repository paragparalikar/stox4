package com.stox.module.charting.plot.info;


import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ValuePlotInfoPane implements PlotInfoPane {

	private final PlotInfoPane delegate;
	private final Label valueLabel = new Label();

	public ValuePlotInfoPane(final PlotInfoPane delegate) {
		this.delegate = delegate;
		valueLabel.getStyleClass().add("value-label");
		delegate.getNode().getChildren().add(valueLabel);
	}

	public ValuePlotInfoPane setValue(final String text) {
		valueLabel.setText(text);
		return this;
	}

	public ValuePlotInfoPane bind(final ObjectProperty<Color> colorProperty) {
		valueLabel.textFillProperty().bind(colorProperty);
		delegate.bind(colorProperty);
		return this;
	}

	@Override
	public HBox getNode() {
		return delegate.getNode();
	}

	@Override
	public ValuePlotInfoPane setName(String name) {
		delegate.setName(name);
		return this;
	}

	@Override
	public ValuePlotInfoPane addRemoveEventHandler(EventHandler<ActionEvent> eventHandler) {
		delegate.addRemoveEventHandler(eventHandler);
		return this;
	}

	@Override
	public ValuePlotInfoPane addVisibilityEventHandler(EventHandler<ActionEvent> eventHandler) {
		delegate.addVisibilityEventHandler(eventHandler);
		return this;
	}

}
