package com.stox.module.charting.screen;

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

public class ScreenToolBox extends HBox implements ChartingToolBox {

	private final ChartingView chartingView;
	private final FxMessageSource messageSource;
	private final Button screenChooserButton = new FluentButton(Icon.FILTER)
			.onAction(event -> show())
			.classes("icon","primary","small");
	
	public ScreenToolBox(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		this.chartingView = chartingView;
		this.messageSource = messageSource;
		getChildren().add(screenChooserButton);
		Ui.box(this);
	}
	
	private void show() {
		new ScreenChooserModal(chartingView, messageSource).show(this);
	}
	
	@Override
	public Node getNode() {
		return this;
	}
}
