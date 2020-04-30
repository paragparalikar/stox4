package com.stox.module.charting.unit;

import com.stox.fx.widget.Ui;
import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.core.model.Bar;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class OhlcPriceUnit implements PriceUnit {

	private final Parent<Node> parent;
	private final Configuration configuration;
	private final Line line = new Line();
	private final Line open = new Line();
	private final Line close = new Line();

	public OhlcPriceUnit(Parent<Node> parent, Configuration configuration) {
		super();
		this.parent = parent;
		this.configuration = configuration;
		line.getStyleClass().add("price-unit");
		open.getStyleClass().add("price-unit");
		close.getStyleClass().add("price-unit");
		open.strokeProperty().bind(line.strokeProperty());
		close.strokeProperty().bind(line.strokeProperty());
	}

	@Override
	public void update(int index, Bar model, Bar previousModel, XAxis xAxis, YAxis yAxis) {
		final double x = xAxis.getX(index);
		final double width = xAxis.getUnitWidth();
		final double wickX = Ui.px(x + width / 2);
		line.setStartX(wickX);
		line.setEndX(wickX);
		line.setStartY(yAxis.getY(model.high()));
		line.setEndY(yAxis.getY(model.low()));

		open.setStartX(wickX - width / 4);
		open.setEndX(wickX);
		close.setStartX(wickX);
		close.setEndX(wickX + width / 4);

		final double openY = yAxis.getY(model.open());
		open.setStartY(openY);
		open.setEndY(openY);

		final double closeY = yAxis.getY(model.close());
		close.setStartY(closeY);
		close.setEndY(closeY);

		final boolean up = null != previousModel && model.close() > previousModel.close();
		final ObjectProperty<Color> colorProperty = up ? configuration.upBarColorProperty() : configuration.downBarColorProperty();
		line.strokeProperty().unbind();
		line.strokeProperty().bind(colorProperty);
	}

	@Override
	public void attach() {
		parent.addAll(line, open, close);
	}

	@Override
	public void detach() {
		parent.removeAll(line, open, close);
	}

}
