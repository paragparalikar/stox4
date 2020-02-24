package com.stox.module.charting.axis.vertical;

import com.stox.module.charting.grid.HorizontalGrid;

import lombok.Setter;

@Setter
public class PrimaryValueAxis extends ValueAxis {

	private HorizontalGrid horizontalGrid;

	public PrimaryValueAxis(HorizontalGrid horizontalGrid) {
		super(20);
		this.horizontalGrid = horizontalGrid;
	}

	@Override
	public void layoutChartChildren(YAxis yAxis) {
		horizontalGrid.reset();
		super.layoutChartChildren(yAxis);
		horizontalGrid.toBack();
	}

	@Override
	protected void tick(Double value, YAxis yAxis) {
		super.tick(value, yAxis);
		horizontalGrid.addLine(yAxis.getY(value));
	}

}
