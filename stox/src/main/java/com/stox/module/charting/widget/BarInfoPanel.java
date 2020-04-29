package com.stox.module.charting.widget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.stox.fx.widget.Spacer;
import com.stox.module.core.model.Bar;
import com.stox.util.Strings;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BarInfoPanel extends HBox {

	private final Label open = new Label("O:");
	private final Label openValue = new Label();
	private final Label high = new Label("H:");
	private final Label highValue = new Label();
	private final Label low = new Label("L:");
	private final Label lowValue = new Label();
	private final Label close = new Label("C:");
	private final Label closeValue = new Label();
	private final Label volume = new Label("V:");
	private final Label volumeValue = new Label();
	private final Label date = new Label("D:");
	private final Label dateValue = new Label();
	private final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");

	public BarInfoPanel() {
		setVisible(false);
		getStyleClass().add("bar-info-panel");
		open.getStyleClass().add("name-label");
		high.getStyleClass().add("name-label");
		low.getStyleClass().add("name-label");
		close.getStyleClass().add("name-label");
		volume.getStyleClass().add("name-label");
		date.getStyleClass().add("name-label");
		openValue.getStyleClass().add("value-label");
		highValue.getStyleClass().add("value-label");
		lowValue.getStyleClass().add("value-label");
		closeValue.getStyleClass().add("value-label");
		volumeValue.getStyleClass().add("value-label");
		dateValue.getStyleClass().add("date-value-label");
		getChildren().addAll(open, openValue, high, highValue, low, lowValue, close, closeValue, volume, volumeValue,
				date, dateValue, new Spacer());
	}

	public void set(Bar bar) {
		if (null == bar) {
			setVisible(false);
			openValue.setText(null);
			highValue.setText(null);
			lowValue.setText(null);
			closeValue.setText(null);
			volumeValue.setText(null);
			dateValue.setText(null);
		} else {
			setVisible(true);
			openValue.setText(Strings.stringValueOf(bar.getOpen()));
			highValue.setText(Strings.stringValueOf(bar.getHigh()));
			lowValue.setText(Strings.stringValueOf(bar.getLow()));
			closeValue.setText(Strings.stringValueOf(bar.getClose()));
			volumeValue.setText(Strings.stringValueOf(bar.getVolume()));
			dateValue.setText(dateFormat.format(new Date(bar.getDate())));
		}
	}

	public void reset() {
		set(null);
	}
}
