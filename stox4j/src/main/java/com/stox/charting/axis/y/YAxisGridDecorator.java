package com.stox.charting.axis.y;

import com.stox.charting.grid.HorizontalGrid;

import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class YAxisGridDecorator {
	
	public YAxis decorate(YAxis yAxis, Pane container, HorizontalGrid horizontalGrid) {
		container.getChildren().addListener(
				(ListChangeListener<Node>)change -> changed(change, horizontalGrid));
		return yAxis;
	}
	
	private void changed(Change<? extends Node> change, HorizontalGrid horizontalGrid) {
		while(change.next()) {
			if(change.wasAdded()) {
				for(Node node : change.getAddedSubList()) {
					if(node instanceof Label) {
						final Label label = Label.class.cast(node);
						final Line line = horizontalGrid.addLine(label.getLayoutY() + label.getHeight() / 2);
						line.startYProperty().bind(label.layoutYProperty().add(label.heightProperty().divide(2)));
					}
				}
			}
			if(change.wasRemoved()) {
				if(change.getList().isEmpty()) {
					horizontalGrid.reset();
				}
			}
		}
	}
}
