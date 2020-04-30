package com.stox.module.charting.indicator.unit.parent;

import java.util.List;

import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.parent.UnitParent;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class ChannelUnitParent implements UnitParent<Point2D> {

	private final Group group;
	private final Polygon background = new Polygon();
	private final Polyline upper = new Polyline(), middle= new Polyline(), lower= new Polyline();

	public ChannelUnitParent(final Group group) {
		this.group = group;
		background.setOpacity(0.3);
		group.getChildren().addAll(background, upper, middle, lower);
	}
	
	@Override
	public void clear() {
		background.getPoints().clear();
		upper.getPoints().clear();
		middle.getPoints().clear();
		lower.getPoints().clear();
	}

	@Override
	public boolean isEmpty() {
		return upper.getPoints().isEmpty();
	}
	
	@Override
	public void preLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		clear();
	}

	@Override
	public void postLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		background.getPoints().addAll(upper.getPoints());
		final List<Double> values = lower.getPoints();
		for(int index = values.size() - 1; index > 0; index-=2){
			background.getPoints().addAll(lower.getPoints().get(index - 1), lower.getPoints().get(index));
		}
	}
	
	@Override
	public Node getNode() {
		return group;
	}
	
	@Override
	public void add(Point2D child) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addAll(Point2D... children) {
		if(3 != children.length){
			throw new IllegalArgumentException();
		}
		upper.getPoints().addAll(children[0].getX(), children[0].getY());
		middle.getPoints().addAll(children[1].getX(), children[1].getY());
		lower.getPoints().addAll(children[2].getX(), children[2].getY());
	}

	@Override
	public void remove(Point2D child) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAll(Point2D... children) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void unbindColorProperty() {
		background.fillProperty().unbind();
	}

	@Override
	public void bindColorProperty(ObjectProperty<Color> colorProperty) {
		background.fillProperty().bind(colorProperty);
	}


}
