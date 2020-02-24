package com.stox.module.charting.plot.info;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public interface PlotInfoPane {

	HBox getNode();
	
	PlotInfoPane setName(final String name);
	
	PlotInfoPane bind(final ObjectProperty<Color> colorProperty);
	
	PlotInfoPane addRemoveEventHandler(final EventHandler<ActionEvent> eventHandler);
	
	PlotInfoPane addVisibilityEventHandler(final EventHandler<ActionEvent> eventHandler);

}
