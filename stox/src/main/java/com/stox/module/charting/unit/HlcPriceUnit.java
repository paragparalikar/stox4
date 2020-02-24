package com.stox.module.charting.unit;

import com.stox.fx.widget.Ui;
import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.core.model.Bar;

import javafx.scene.Node;
import javafx.scene.shape.Line;

public class HlcPriceUnit implements PriceUnit {

	private final Parent<Node> parent;
	private final Configuration configuration;
	private final Line line = new Line();
	private final Line close = new Line();
	
	public HlcPriceUnit(Parent<Node> parent, Configuration configuration) {
		this.parent = parent;
		this.configuration = configuration;
		line.getStyleClass().add("price-unit");
		close.getStyleClass().add("price-unit");
		close.strokeProperty().bind(line.strokeProperty());
	}

	@Override
	public void update(int index, Bar model, Bar previousModel, XAxis xAxis, YAxis yAxis) {
		final double x = xAxis.getX(index);
		final double width = xAxis.getUnitWidth();
		final double wickX = Ui.px(x + width / 4);
		line.setStartX(wickX);
		line.setEndX(wickX);
		line.setStartY(yAxis.getY(model.getHigh()));
		line.setEndY(yAxis.getY(model.getLow()));
		close.setStartX(wickX);
		close.setEndX(wickX + width / 2);
		final double y = yAxis.getY(model.getClose());
		close.setStartY(y);
		close.setEndY(y);
		final boolean up = null != previousModel && model.getClose() > previousModel.getClose();
		
		line.strokeProperty().unbind();
		line.strokeProperty().bind(up ? configuration.upBarColorProperty() : configuration.downBarColorProperty());
	}

	@Override
	public void attach() {
		parent.addAll(line, close);
	}

	@Override
	public void detach() {
		parent.removeAll(line, close);
	}

}
