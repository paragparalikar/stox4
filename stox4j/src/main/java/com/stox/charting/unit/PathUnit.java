package com.stox.charting.unit;

import org.ta4j.core.num.Num;

import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.unit.parent.UnitParent;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.PathElement;
import lombok.Setter;

@Setter
public class PathUnit implements Unit<Num, PathElement> {
	
	private XAxis xAxis;
	private YAxis yAxis;
	private ChartingContext context;
	private LineTo lineTo = new LineTo();

	@Override
	public void layoutChildren(int index, Num model, UnitParent<PathElement> parent) {
		lineTo.setX(xAxis.getX(index));
		lineTo.setY(yAxis.getY(model.doubleValue()));
		parent.add(lineTo);
	}

}
