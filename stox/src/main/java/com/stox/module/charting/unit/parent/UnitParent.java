package com.stox.module.charting.unit.parent;

import com.stox.fx.widget.parent.Parent;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.axis.vertical.YAxis;

import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;

public interface UnitParent<C> extends Parent<C> {
	
	default void unbindColorProperty() {
		
	}
	
	default void preLayoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		
	}
	
	default void postLayoutChartChildren(final XAxis xAxis, final YAxis yAxis) {
		
	}
	
	default void bindColorProperty(final ObjectProperty<Color> colorProperty) {
		
	}
	
}
