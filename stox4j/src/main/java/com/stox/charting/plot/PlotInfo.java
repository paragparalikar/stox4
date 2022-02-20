package com.stox.charting.plot;

import com.stox.common.ui.Icon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlotInfo<T> extends HBox {

	private final Label name = new Label();
	private final HBox buttonBar = new HBox();
	
	public PlotInfo(Node node) {
		getStyleClass().add("plot-info-pane");
		name.getStyleClass().add("plot-name");
		getChildren().addAll(buttonBar, name);
		createButton(Icon.EYE, event -> node.setVisible(!node.isVisible()));
	}
	
	protected void createButton(String icon, EventHandler<ActionEvent> handler) {
		final Button button = new Button(icon);
		button.getStyleClass().add("icon");
		buttonBar.getChildren().add(button);
		button.setOnAction(handler);
	}
	
	public void setName(String value) {
		setVisible(null != value);
		name.setText(null == value ? null : value.toUpperCase());
	}
	
	public void setValue(T model) {
		
	}
	
}
