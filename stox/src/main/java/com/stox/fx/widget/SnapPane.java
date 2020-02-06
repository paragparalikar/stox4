package com.stox.fx.widget;

import java.util.Arrays;
import java.util.Objects;

import com.stox.fx.fluent.scene.layout.IFluentBorderPane;
import com.stox.fx.fluent.scene.layout.IFluentRegion;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

public class SnapPane extends Pane {

	private boolean initialized;
	private final double boxSize = 20;
	private final Group group = new Group();
	private double xSize = 20, ySize = 20, x, y, width, height;

	public SnapPane() {
		group.setVisible(false);
		group.setManaged(false);
		getChildren().add(group);
		layoutBoundsProperty().addListener((o, old, bounds) -> rebuild());
		rebuild();
	}

	private void rebuild() {
		if (0 == getHeight() || 0 == getWidth()) {
			return;
		}
		group.getChildren().clear();

		xSize = getWidth() / new Double(getWidth() / boxSize).intValue();
		ySize = getHeight() / new Double(getHeight() / boxSize).intValue();

		double pointer = 0;
		while (pointer < getWidth()) {
			pointer += xSize;
			final Line line = new Line(pointer, 0, pointer, getHeight());
			line.getStyleClass().add("snap-to-grid-line");
			group.getChildren().add(line);
		}

		pointer = 0;
		while (pointer < getHeight()) {
			pointer += ySize;
			final Line line = new Line(0, pointer, getWidth(), pointer);
			line.getStyleClass().add("snap-to-grid-line");
			group.getChildren().add(line);
		}
		initialized = true;
	}

	public void add(final Node knob, final IFluentBorderPane<? extends BorderPane> target) {
		getChildren().add(target.getThis());
		final EventHandler<MouseEvent> mousePressedHandler = e -> mousePressed(target);
		final EventHandler<MouseEvent> mouseReleasedHandler = e -> mouseReleased(target);
		bind(mousePressedHandler, mouseReleasedHandler, knob, target.top(), target.right(), target.bottom(), target.left());
		target.getThis().parentProperty().addListener((o,old,value) -> {
			if(Objects.equals(SnapPane.this, old) && Objects.isNull(value)) {
				unbind(mousePressedHandler, mouseReleasedHandler, knob, target.top(), target.right(), target.bottom(), target.left());
			}
		});
		snap(target);
	}

	private void bind(final EventHandler<MouseEvent> mousePressedHandler, final EventHandler<MouseEvent> mouseReleasedHandler, Node... nodes) {
		Arrays.asList(nodes).forEach(node -> {
			node.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
			node.addEventHandler(MouseEvent.MOUSE_DRAGGED, mousePressedHandler);
			node.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
		});
	}
	
	private void unbind(final EventHandler<MouseEvent> mousePressedHandler, final EventHandler<MouseEvent> mouseReleasedHandler, Node... nodes) {
		Arrays.asList(nodes).forEach(node -> {
			node.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
			node.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mousePressedHandler);
			node.removeEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedHandler);
		});
	}
	
	private void mousePressed(IFluentRegion<? extends Region> region) {
		if (!initialized)
			rebuild();
		group.setVisible(true);
		x = Math.max(0, snap(region.layoutX(), xSize));
		y = Math.max(0, snap(region.layoutY(), ySize));
		height = Math.min(getHeight(), snap(region.layoutY() + region.height(), ySize) - y);
		width = Math.min(getWidth(), snap(region.layoutX() + region.width(), xSize) - x);
	}

	private void mouseReleased(IFluentRegion<? extends Region> region) {
		region.bounds(x, y, width, height);
		group.setVisible(false);
	}
	
	private double maxHeight(final IFluentRegion<? extends Region> region) {
		return Math.max(Math.max(region.height(), region.prefHeight()),
				Math.max(region.minHeight(), region.maxHeight()));
	}
	
	private double maxWidth(final IFluentRegion<? extends Region> region) {
		return Math.max(Math.max(region.width(), region.prefWidth()),
				Math.max(region.minWidth(), region.maxWidth()));
	}

	private void snap(final IFluentRegion<? extends Region> region) {
		final double x = Math.max(0, snap(region.layoutX(), xSize));
		final double y = Math.max(0, snap(region.layoutY(), ySize));
		final double currentWidth = maxWidth(region);
		double width = Math.min(getWidth(), snap(currentWidth, xSize));
		width = xSize > (getWidth() - (x + width)) ? getWidth() - x : width;
		final double currentHeight = maxHeight(region);
		double height = Math.min(getHeight(), snap(currentHeight, ySize));
		height = ySize > (getHeight() - (y + height)) ? getHeight() - y : height;
		region.bounds(x < xSize ? 0 : x, y < ySize ? 0 : y, width, height);
	}

	private double snap(double value, double size) {
		double rem = value % size;
		return ((int) (rem < size / 2 ? value - rem : value - rem + size));
	}
}
