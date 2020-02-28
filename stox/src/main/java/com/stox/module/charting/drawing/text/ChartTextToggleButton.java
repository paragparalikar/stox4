package com.stox.module.charting.drawing.text;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.AbstractDrawingToggleButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class ChartTextToggleButton extends AbstractDrawingToggleButton
		implements ChangeListener<Boolean> {

	public ChartTextToggleButton(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		super(chartingView);
		setText("A");
		classes("icon", "primary").text("A").tooltip(Ui.tooltip(messageSource.get("Text")));
		selectedProperty().addListener(this);
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		super.changed(observable, oldValue, newValue);
		final ChartingView chartingView = getChartingView();
		if (newValue && null != chartingView) {
			chartingView.mouseModeHandler(new ChartTextModeMouseHandler(chartingView, () -> setSelected(false)));
		}
	}

}
