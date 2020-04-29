package com.stox.module.charting.indicator.addin;

import com.stox.fx.widget.auto.AutoWidget;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;
import com.stox.module.charting.unit.Unit;

import javafx.scene.Node;

public interface ChartAddIn<MODEL>  {

	void update();
	
	Node getNode();
	
	AutoWidget getEditor();

	void preLayoutChartChildren(final XAxis xAxis, final YAxis yAxis);

	void layoutUnit(final MODEL model, final Unit<MODEL> unit, final XAxis xAxis, final YAxis yAxis);

	void postLayoutChartChildren(final XAxis xAxis, final YAxis yAxis);

}
