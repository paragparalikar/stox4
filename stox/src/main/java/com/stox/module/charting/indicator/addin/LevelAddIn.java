package com.stox.module.charting.indicator.addin;

import java.util.concurrent.Callable;

import com.stox.fx.widget.Ui;
import com.stox.fx.widget.auto.AutoWidget;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.Unit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

public class LevelAddIn<M> implements ChartAddIn<M> {

	@Getter
	public static class Configuration {

		private final DoubleProperty lineWidthProperty = new SimpleDoubleProperty(1);

		private final DoubleProperty lineOpacityProperty = new SimpleDoubleProperty(1);

		private final ObjectProperty<Color> lineColorProperty = new SimpleObjectProperty<>(Color.GRAY);

	}

	@Setter(AccessLevel.PROTECTED)
	private double level = 0;
	
	@Getter(AccessLevel.PROTECTED)
	private Line line = new Line();
	
	private final Callable<Double> updater;
	
	@Getter(AccessLevel.PROTECTED)
	private final Configuration configuration = new Configuration();

	public LevelAddIn(final Callable<Double> updater) {
		this.updater = updater;
		line.setManaged(false);
		line.getStyleClass().add("line");
		line.endYProperty().bind(line.startYProperty());
		line.strokeWidthProperty().bind(configuration.getLineWidthProperty());
		line.strokeProperty().bind(configuration.getLineColorProperty());
		line.opacityProperty().bind(configuration.getLineOpacityProperty());
	}

	@Override
	public AutoWidget getEditor() {
		return null;
	}

	@Override
	public Node getNode() {
		return line;
	}

	@Override
	@SneakyThrows
	public void update() {
		level = updater.call();
	}

	@Override
	public void postLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		line.setEndX(xAxis.getWidth());
		line.setStartY(Ui.px(yAxis.getY(level)));
	}

	@Override
	public void layoutUnit(M model, Unit<M> unit, XAxis xAxis, YAxis yAxis) {

	}

	@Override
	public void preLayoutChartChildren(XAxis xAxis, YAxis yAxis) {
		line.toBack();
	}

}
