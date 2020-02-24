package com.stox.module.charting.axis.horizontal;

import java.util.Date;
import java.util.List;

import com.stox.fx.widget.StaticLayoutPane;
import com.stox.module.charting.grid.VerticalGrid;
import com.stox.module.core.model.Bar;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class DateTimeAxis extends StaticLayoutPane {
	private static final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec" };

	private double width;
	private VerticalGrid verticalGrid;

	public DateTimeAxis(final VerticalGrid verticalGrid) {
		this.verticalGrid = verticalGrid;
		getStyleClass().add("date-axis");
		setPadding(2, 2, 2, 2);
		setLabelWidth(new Text("444444").getLayoutBounds().getWidth());
		width = getLabelWidth() + getPaddingLeft() + getPaddingRight();
	}

	public void layoutChartChildren(final XAxis xAxis, final List<Bar> bars) {
		getChildren().clear();
		if (null != verticalGrid) {
			verticalGrid.reset();
		}
		if (!bars.isEmpty()) {
			Date last = null;
			final int increment = (int) Math.ceil(width / xAxis.getUnitWidth());
			for (int index = xAxis.getClippedEndIndex(); index >= xAxis.getClippedStartIndex(); index -= increment) {
				final Date date = new Date(bars.get(index).getDate());
				final double x = xAxis.getX(index);
				add(x, 0, new Label(getText(last, date)));
				last = date;
				if (null != verticalGrid) {
					verticalGrid.addLine(x);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private String getText(final Date previous, final Date current) {
		if (null == previous || previous.getYear() != current.getYear()) {
			return String.valueOf(1900 + current.getYear());
		} else if (previous.getMonth() != current.getMonth()) {
			return MONTHS[current.getMonth()];
		} else {
			return String.valueOf(current.getDate());
		}
	}


}
