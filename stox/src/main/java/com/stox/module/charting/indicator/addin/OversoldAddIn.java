package com.stox.module.charting.indicator.addin;

import java.util.concurrent.Callable;

import com.stox.module.charting.axis.Updatable;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class OversoldAddIn<M> extends MovableLevelAddIn<M> implements Updatable {

	@Getter
	@RequiredArgsConstructor
	public static class Configuration {

		private final LevelAddIn.Configuration lineConfiguration;

		private final DoubleProperty overboughtAreaOpacityProperty = new SimpleDoubleProperty(0.5);

		private final ObjectProperty<Color> overboughtAreaColorProperty = new SimpleObjectProperty<>(
				Color.web("#5cb85c"));

	}

	private final Polyline polyline;
	private final Group group = new Group();
	private final Configuration configuration;

	public OversoldAddIn(final Polyline polyline, Callable<Double> updater) {
		super(updater);
		this.polyline = polyline;
		this.configuration = new Configuration(getConfiguration());
	}

	@Override
	public Group getNode() {
		return group;
	}

	@Override
	protected void mouseDragged(MouseEvent event) {
		super.mouseDragged(event);
		if (isMovable()) {
			group.fireEvent(new UpdatableRequestEvent(this));
		}
	}

	@Override
	public void postLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		super.postLayoutChartChildren(xAxis, yAxis);
		if (!isMovable()) {
			update(xAxis, yAxis);
		}
	}

	@Override
	public void update(XAxis xAxis, YAxis yAxis) {
		if (4 <= polyline.getPoints().size()) {
			final Line line = getLine();
			group.getChildren().setAll(line);

			final Polygon polygon = new Polygon(xAxis.getWidth(), 0);
			polygon.getPoints().addAll(polyline.getPoints());
			polygon.getPoints().addAll(0d, 0d);
			final Rectangle rectangle = new Rectangle(0, line.getStartY(), xAxis.getWidth(),
					yAxis.getHeight() - line.getStartY());

			final Shape area = Shape.intersect(polygon, rectangle);
			area.setFill(configuration.getOverboughtAreaColorProperty().get());
			area.setOpacity(configuration.getOverboughtAreaOpacityProperty().get());
			group.getChildren().add(area);
		}
	}

}
