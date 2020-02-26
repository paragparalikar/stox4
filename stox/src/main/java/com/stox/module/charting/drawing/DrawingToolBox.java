package com.stox.module.charting.drawing;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.segment.trend.TrendSegmentToggleButton;
import com.stox.module.charting.tools.ChartingToolBox;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import lombok.NonNull;

public class DrawingToolBox extends HBox implements ChartingToolBox {
	
	public DrawingToolBox(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		getChildren().addAll(
				new TrendSegmentToggleButton(chartingView),
//				new HorizontalSegmentToggleButton(chartingView),
//				new VerticalSegmentToggleButton(chartingView),
//				new ChartRegionToggleButton(chartingView),
//				new ChartTextToggleButton(chartingView),
				new ClearDrawingsButton(chartingView));
		Ui.box(this);
	}
	
	@Override
	public Node getNode() {
		return this;
	}

}
