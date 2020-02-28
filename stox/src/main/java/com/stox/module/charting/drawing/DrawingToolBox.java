package com.stox.module.charting.drawing;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.region.ChartRegionToggleButton;
import com.stox.module.charting.drawing.segment.horizontal.HorizontalSegmentToggleButton;
import com.stox.module.charting.drawing.segment.trend.TrendSegmentToggleButton;
import com.stox.module.charting.drawing.segment.vertical.VerticalSegmentToggleButton;
import com.stox.module.charting.drawing.text.ChartTextToggleButton;
import com.stox.module.charting.tools.ChartingToolBox;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.NonNull;

public class DrawingToolBox extends HBox implements ChartingToolBox {
	
	public DrawingToolBox(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		getChildren().addAll(
				new TrendSegmentToggleButton(messageSource, chartingView),
				new HorizontalSegmentToggleButton(messageSource, chartingView),
				new VerticalSegmentToggleButton(messageSource, chartingView),
				new ChartRegionToggleButton(messageSource, chartingView),
				new ChartTextToggleButton(messageSource, chartingView),
				new ClearDrawingsButton(messageSource, chartingView));
		Ui.box(this);
	}
	
	@Override
	public Node getNode() {
		return this;
	}

}
