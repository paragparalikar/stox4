package com.stox.module.charting.indicator;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;
import com.stox.module.charting.tools.ChartingToolBox;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import lombok.NonNull;

public class IndicatorToolBox extends HBox implements ChartingToolBox {

	private final ChartingView chartingView;
	private final FxMessageSource messageSource;
	private final Button indicatorChooserButton = new FluentButton(Icon.LINE_CHART)
			.onAction(event -> show())
			.classes("icon","primary","small");
	
	public IndicatorToolBox(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		this.chartingView = chartingView;
		this.messageSource = messageSource;
		getChildren().add(indicatorChooserButton);
		Ui.box(this);
	}
	
	private void show() {
		new IndicatorChooserModal(chartingView, messageSource).show(this);
	}
	
	@Override
	public Node getNode() {
		return this;
	}

}
