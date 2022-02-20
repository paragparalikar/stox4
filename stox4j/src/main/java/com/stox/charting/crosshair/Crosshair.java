package com.stox.charting.crosshair;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import lombok.Getter;

@Getter
public class Crosshair extends Group {

	private final Region region;
	private final Line verticalLine = new Line();
	private final Line horizontalLine = new Line();
	
	public Crosshair(Region region) {
		this.region = region;
		setMouseTransparent(true);
		setAutoSizeChildren(false);
		verticalLine.setManaged(false);
		horizontalLine.setManaged(false);
		verticalLine.getStyleClass().addAll("crosshair", "vertical");
		horizontalLine.getStyleClass().addAll("crosshair", "horizontal");
		getChildren().addAll(verticalLine, horizontalLine);
		region.addEventHandler(MouseEvent.MOUSE_MOVED, this::onMouseMoved);
		region.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseMoved);
	}

	private void onMouseMoved(MouseEvent event) {
		verticalLine.setStartX(event.getX());
		verticalLine.setEndX(event.getX());
		verticalLine.setStartY(region.getLayoutY() + 1);
		verticalLine.setEndY(region.getLayoutY() + region.getHeight() - 1);
		horizontalLine.setStartY(event.getY());
		horizontalLine.setEndY(event.getY());
		horizontalLine.setStartX(region.getLayoutX() + 1);
		horizontalLine.setEndX(region.getLayoutX() + region.getWidth() - 1);
	}
	
}
