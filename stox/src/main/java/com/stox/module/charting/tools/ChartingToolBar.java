package com.stox.module.charting.tools;

import java.util.Arrays;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.DrawingToolBox;
import com.stox.module.charting.indicator.IndicatorToolBox;

import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.NonNull;

public class ChartingToolBar extends ToolBar {

	public ChartingToolBar(@NonNull final ChartingView chartingView, @NonNull final FxMessageSource messageSource) {
		HBox.setHgrow(this, Priority.ALWAYS);
		setMaxWidth(Double.MAX_VALUE);
		getStyleClass().add("primary");
		Arrays.asList(
				new BarSpanToolBox(messageSource, chartingView), 
				new PriceUnitTypeToolBox(messageSource, chartingView),
				new DrawingToolBox(messageSource, chartingView),
				new IndicatorToolBox(messageSource, chartingView))
		.forEach(box -> {
			getItems().add(box.getNode());
		});
	}

}
