package com.stox.common.ui;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Fx {
	
	public final PseudoClass PSEUDO_CLASS_DRAGOVER = PseudoClass.getPseudoClass("dragOver");

	public void run(Runnable runnable) {
		if(null != runnable) {
			if(Platform.isFxApplicationThread()) {
				runnable.run();
			} else {
				Platform.runLater(runnable);
			}
		}
	}

	public Node spacer() {
		final Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);
		VBox.setVgrow(region, Priority.ALWAYS);
		region.setMaxHeight(Double.MAX_VALUE);
		region.setMaxWidth(Double.MAX_VALUE);
		return region;
	}
	
	public Node icon(String value) {
		final Label label = new Label(value);
		label.getStyleClass().add("icon");
		return label;
	}
	
	public String toString(Color color) {
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
	
}
