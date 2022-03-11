package com.stox.charting;

import lombok.Getter;

@Getter
public class ChartingConfig {
	private int fetchSize = 200;
	private double maxUnitWidthProperty = 50;
	private double minUnitWidthProperty = 1;
	private double paddingTopProperty = 10;
	private double paddingBottomProperty = 8;
}