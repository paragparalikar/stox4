package com.stox.charting.grid;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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
		getChildren().addAll(verticalLine, horizontalLine);
		

		final Color color = Color.BLUEVIOLET;
		final List<Double> offsets = Arrays.asList(10d, 5d, 5d, 5d);
		verticalLine.setStroke(color);
		verticalLine.getStrokeDashArray().addAll(offsets);
		horizontalLine.setStroke(color);
		horizontalLine.getStrokeDashArray().addAll(offsets);
		
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
