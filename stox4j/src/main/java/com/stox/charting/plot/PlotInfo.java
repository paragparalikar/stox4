package com.stox.charting.plot;

import java.util.Optional;

import com.stox.common.ui.Icon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter 
public class PlotInfo<T> extends HBox {

	private final Plot<T, ?, ?> plot;
	private final Label name = new Label();
	private final Label configInfo = new Label();
	private final HBox buttonBar = new HBox();
	
	public PlotInfo(Plot<T, ?, ?> plot) {
		this.plot = plot;
		getStyleClass().add("plot-info");
		name.getStyleClass().add("plot-name");
		configInfo.getStyleClass().add("plot-config-info");
		buttonBar.getStyleClass().add("plot-info-button-bar");
		getChildren().addAll(name, configInfo, buttonBar);
		createButton(Icon.EYE, event -> plot.setVisible(!plot.isVisible()));
	}
	
	protected void createButton(String icon, EventHandler<ActionEvent> handler) {
		final Button button = new Button(icon);
		button.getStyleClass().add("icon");
		buttonBar.getChildren().add(button);
		button.setOnAction(handler);
	}
	
	public void setName(String value) {
		setVisible(null != value);
		name.setText(value);
	}
	
	public void setConfigInfo(String text) {
		Optional.ofNullable(text).ifPresent(value -> {
			configInfo.setText("{" + text + "}");
		});
	}
	
	public void setValue(T model) {
		
	}
	
	public void setColor(Color color) {
		name.setTextFill(color);
		configInfo.setTextFill(color);
	}
	
}
