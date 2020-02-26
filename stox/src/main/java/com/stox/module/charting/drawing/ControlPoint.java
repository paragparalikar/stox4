package com.stox.module.charting.drawing;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.axis.Updatable;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class ControlPoint extends Circle implements Updatable, EventHandler<MouseEvent> {

	@SerializedName("x")
	private double x;

	@SerializedName("y")
	private double y;

	@SerializedName("location")
	private final Location location = new Location();

	public ControlPoint() {
		build();
		bind();
	}

	private void build() {
		setRadius(5);
		setManaged(false);
		getStyleClass().add("control-point");
	}

	private void bind() {
		addEventHandler(MouseEvent.MOUSE_PRESSED, this);
		addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
	}

	public long getDate() {
		return location.getDate();
	}

	public double getValue() {
		return location.getValue();
	}

	@Override
	public void update(final XAxis xAxis, final YAxis yAxis) {
		location.setDate(xAxis.getDate(getCenterX()));
		location.setValue(yAxis.getValue(getCenterY()));
	}

	public void layoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		setCenterX(xAxis.getX(location.getDate()));
		setCenterY(yAxis.getY(location.getValue()));
	}

	@Override
	public void handle(MouseEvent event) {
		if (!event.isConsumed()) {
			if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
				x = event.getX();
				y = event.getY();
				event.consume();
			} else if (MouseEvent.MOUSE_DRAGGED.equals(event.getEventType())) {
				setCenterX(event.getX() + getCenterX() - x);
				setCenterY(event.getY() + getCenterY() - y);
				x = event.getX();
				y = event.getY();
				event.consume();
				fireEvent(new UpdatableRequestEvent(this));
			}
		}
	}

}
