package com.stox.module.charting.drawing;

import com.stox.fx.widget.HasNode;
import com.stox.module.charting.axis.Updatable;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class ControlPoint implements Updatable, EventHandler<MouseEvent>, HasNode<Node> {

	private double x, y;

	@Getter
	private final Location location = new Location();
	
	private final Circle circle = new Circle(); 

	public ControlPoint() {
		build();
		bind();
	}
	
	public ControlPoint location(final Location location) {
		this.location.state(location);
		return this;
	}
	
	private void build() {
		circle.setRadius(5);
		circle.setManaged(false);
		circle.getStyleClass().add("control-point");
	}

	private void bind() {
		circle.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
		circle.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
	}

	public long getDate() {
		return location.date();
	}

	public double getValue() {
		return location.value();
	}

	@Override
	public void update(final XAxis xAxis, final YAxis yAxis) {
		location.date(xAxis.getDate(circle.getCenterX()));
		location.value(yAxis.getValue(circle.getCenterY()));
	}

	public void layoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		circle.setCenterX(xAxis.getX(location.date()));
		circle.setCenterY(yAxis.getY(location.value()));
	}

	@Override
	public void handle(MouseEvent event) {
		if (!event.isConsumed()) {
			if (MouseEvent.MOUSE_PRESSED.equals(event.getEventType())) {
				x = event.getX();
				y = event.getY();
				event.consume();
			} else if (MouseEvent.MOUSE_DRAGGED.equals(event.getEventType())) {
				circle.setCenterX(event.getX() + circle.getCenterX() - x);
				circle.setCenterY(event.getY() + circle.getCenterY() - y);
				x = event.getX();
				y = event.getY();
				event.consume();
				circle.fireEvent(new UpdatableRequestEvent(this));
			}
		}
	}
	
	public double getCenterX() {
		return circle.getCenterX();
	}
	
	public void setCenterX(final double value) {
		circle.setCenterX(value);
	}
	
	public ObservableValue<? extends Number> centerXProperty(){
		return circle.centerXProperty();
	}
	
	public double getCenterY() {
		return circle.getCenterY();
	}
	
	public void setCenterY(final double value) {
		circle.setCenterY(value);
	}
	
	public ObservableValue<? extends Number> centerYProperty(){
		return circle.centerYProperty();
	}
	
	@Override
	public Node getNode() {
		return circle;
	}

}
