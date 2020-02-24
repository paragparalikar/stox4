package com.stox.module.charting.unit;

import com.stox.fx.fluent.scene.layout.FluentRegion;
import com.stox.fx.widget.Ui;
import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BarUnit implements Unit<Double> {

	@NonNull
	private final Parent<Node> parent;
	private final Region region = new FluentRegion().classes("bar");

	@Override
	public void attach() {
		parent.add(region);
	}

	@Override
	public void detach() {
		parent.remove(region);
	}

	@Override
	public void update(final int index, final Double model, final Double previousModel, final XAxis xAxis,
			final YAxis yAxis) {
		final double y = Ui.px(yAxis.getY(model));
		region.resizeRelocate(xAxis.getX(index), y, xAxis.getUnitWidth(), yAxis.getHeight() - y);
	}

	public void setOpacity(double value) {
		region.setOpacity(value);
	}

}
