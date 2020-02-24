package com.stox.module.charting.unit;

import com.stox.fx.fluent.scene.layout.FluentRegion;
import com.stox.fx.widget.Ui;
import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.Configuration;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.core.model.Bar;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CandlePriceUnit implements PriceUnit {

	private final Parent<Node> parent;
	private final Configuration configuration;
	private final Line wick = new Line();
	private final Region body = new FluentRegion().classes("candle-body", "price-unit");

	@Override
	public void attach() {
		parent.addAll(wick, body);
		wick.getStyleClass().add("candle-wick");
		wick.strokeProperty().bind(configuration.wickColorProperty());
		wick.strokeWidthProperty().bind(configuration.wickWidthProperty());
	}

	@Override
	public void detach() {
		parent.removeAll(wick, body);
		wick.strokeProperty().unbind();
		wick.strokeWidthProperty().unbind();
	}

	@Override
	public void update(int index, Bar model, Bar previousModel, XAxis xAxis, YAxis yAxis) {
		final double x = xAxis.getX(index);
		final double width = xAxis.getUnitWidth();
		final double wickX = (int)(x + width / 2);
		wick.setStartX(wickX);
		wick.setEndX(wickX);
		wick.setStartY(yAxis.getY(model.getHigh()));
		wick.setEndY(yAxis.getY(model.getLow()));

		final double bodyTop = yAxis.getY(Math.max(model.getOpen(), model.getClose()));
		final double bodyHeight = yAxis.getY(Math.min(model.getOpen(), model.getClose())) - bodyTop;
		body.resizeRelocate(Ui.px(x + width * 0.2), bodyTop, width * 0.6, bodyHeight);
		body.setBorder(model.getClose() > model.getOpen() ? configuration.upBarBorder() : configuration.downBarBorder());
		body.setBackground(model.getClose() > model.getOpen() ? configuration.upBarBackground() : configuration.downBarBackground());
	}

}
