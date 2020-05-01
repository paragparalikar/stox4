package com.stox.module.charting.screen.unit;

import com.stox.fx.widget.Icon;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.screen.ScreenMatch;
import com.stox.module.core.model.Bar;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;

public class BearishScreenUnit extends AbstractScreenUnit {

	public BearishScreenUnit(final Group parent) {
		super(Icon.ARROW_DOWN, "bearish", parent);
	}

	@Override
	public void update(int index, ScreenMatch model, ScreenMatch previousModel, XAxis xAxis, YAxis yAxis) {
		final Bar bar = model.bar();
		final Polygon node = getNode();
		final double x = xAxis.getX(model.index());
		final double width = Math.max(10, xAxis.getUnitWidth());
		final double height = 0.7 * width;
		final double y = yAxis.getY(bar.high()) - 5 - height;
		node.getPoints().setAll(x, y, x + width, y, x + width / 2, y + height);
	}

}
