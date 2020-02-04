package com.stox.fx.widget;

import com.stox.fx.fluent.scene.layout.FluentRegion;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Spacer extends FluentRegion {

	public Spacer() {
		setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(this, Priority.ALWAYS);
		setMaxHeight(Double.MAX_VALUE);
		VBox.setVgrow(this, Priority.ALWAYS);
	}
	
}
