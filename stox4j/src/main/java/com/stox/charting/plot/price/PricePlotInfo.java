package com.stox.charting.plot.price;

import org.ta4j.core.Bar;

import com.stox.charting.plot.PlotInfo;
import com.stox.common.scrip.Scrip;
import com.stox.common.util.Colors;
import com.stox.common.util.Strings;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PricePlotInfo extends PlotInfo<Bar>{
	private final Label nameLabel = new Label();
	private final Label openLabel = new Label();
	private final Label highLabel = new Label();
	private final Label lowLabel = new Label();
	private final Label closeLabel = new Label();
	private final HBox priceInfoContainer = new HBox(
			new HBox(new Label("O "), openLabel), 
			new HBox(new Label("H "), highLabel), 
			new HBox(new Label("L "), lowLabel),
			new HBox(new Label("C "), closeLabel));
	
	public PricePlotInfo() {
		getStyleClass().add("plot-info-pane");
		nameLabel.getStyleClass().add("scrip-name");
		priceInfoContainer.getStyleClass().add("plot-info-values");
		getChildren().addAll(nameLabel, priceInfoContainer);
	}
	
	public void set(Scrip scrip) {
		if(null != scrip) {
			nameLabel.setVisible(true);
			nameLabel.setText(scrip.getName().toUpperCase());
		} else {
			nameLabel.setVisible(false);
		}
	}
	
	@Override
	public void setValue(Bar bar) {
		if(null != bar) {
			priceInfoContainer.setVisible(true);
			final Color color = bar.getOpenPrice().isLessThan(bar.getClosePrice()) ? Colors.UP : Colors.DOWN;
			openLabel.setTextFill(color);
			highLabel.setTextFill(color);
			lowLabel.setTextFill(color);
			closeLabel.setTextFill(color);
			openLabel.setText(Strings.toString(bar.getOpenPrice().doubleValue()));
			highLabel.setText(Strings.toString(bar.getHighPrice().doubleValue()));
			lowLabel.setText(Strings.toString(bar.getLowPrice().doubleValue()));
			closeLabel.setText(Strings.toString(bar.getClosePrice().doubleValue()));
		} else {
			priceInfoContainer.setVisible(false);
		}
	}
}