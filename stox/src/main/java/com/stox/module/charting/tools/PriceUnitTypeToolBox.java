package com.stox.module.charting.tools;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import com.stox.fx.fluent.scene.control.FluentRadioButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;
import com.stox.module.charting.unit.PriceUnitType;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import lombok.NonNull;

public class PriceUnitTypeToolBox extends HBox implements ChartingToolBox {

	private final ChartingView chartingView;
	private final FxMessageSource messageSource;
	private final ToggleGroup toggleGroup = new ToggleGroup();
	private final PriceUnitTypeButtonGraphicFactory factory = new PriceUnitTypeButtonGraphicFactory();

	public PriceUnitTypeToolBox(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		this.chartingView = chartingView;
		this.messageSource = messageSource;
		Arrays.stream(PriceUnitType.values()).forEach(this::buildToggle);
		Ui.box(this);
		toggleGroup.getToggles().stream()
			.filter(toggle -> Objects.equals(chartingView.unitType(), toggle.getUserData()))
			.findFirst()
			.ifPresent(toggle -> toggle.setSelected(true));
	}

	private PriceUnitTypeToolBox buildToggle(final PriceUnitType priceUnitType) {
		final FluentRadioButton radioButton = new FluentRadioButton()
				.removeStyleClass("radio-button")
				.styleClass("toggle-button", "primary", "small")
				.graphic(factory.apply(priceUnitType).getNode())
				.tooltip(Ui.tooltip(messageSource.get(priceUnitType.getName())))
				.toggleGroup(toggleGroup)
				.userData(priceUnitType);
		getChildren().add(radioButton);
		radioButton.selectedProperty().addListener((o, old, value) -> {
			if (value && null != chartingView) {
				chartingView.unitType(priceUnitType);
			}
		});
		return this;
	}

	@Override
	public Node getNode() {
		return this;
	}

}

interface PriceUnitTypeButtonGraphic {

	Node getNode();

}

class PriceUnitTypeButtonGraphicFactory implements Function<PriceUnitType, PriceUnitTypeButtonGraphic> {

	@Override
	public PriceUnitTypeButtonGraphic apply(@NonNull PriceUnitType unitType) {
		switch (unitType) {
			case AREA:
				return new AreaPriceUnitTypeButtonGraphic();
			case CANDLE:
				return new CandlePriceUnitTypeButtonGraphic();
			case HLC:
				return new HlcPriceUnitTypeButtonGraphic();
			case LINE:
				return new LinePriceUnitTypeButtonGraphic();
			case OHLC:
				return new OhlcPriceUnitTypeButtonGraphic();
			default:
				throw new IllegalArgumentException("PriceUnitType " + unitType.getName() + " is not yet supported.");
		}
	}

}

class CandlePriceUnitTypeButtonGraphic implements PriceUnitTypeButtonGraphic {

	private final Line wick = new Line(5, 0, 5, 10);
	private final Region body = new Region();
	private final Group graphic = new Group(wick, body);

	public CandlePriceUnitTypeButtonGraphic() {
		graphic.getStyleClass().add("unit-type-graphic");
		body.setLayoutX(2);
		body.setLayoutY(2.5);
		body.setPrefHeight(6);
		body.setPrefWidth(6);
		body.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
	}

	@Override
	public Node getNode() {
		return graphic;
	}

}

class LinePriceUnitTypeButtonGraphic implements PriceUnitTypeButtonGraphic {

	private final Line line = new Line(0, 10, 10, 0);

	public LinePriceUnitTypeButtonGraphic() {
		line.getStyleClass().add("unit-type-graphic");
	}

	@Override
	public Node getNode() {
		return line;
	}

}

class AreaPriceUnitTypeButtonGraphic implements PriceUnitTypeButtonGraphic {

	private final Polygon area = new Polygon();

	public AreaPriceUnitTypeButtonGraphic() {
		area.getPoints().addAll(0d, 10d, 10d, 0d, 10d, 10d);
		area.setFill(Color.BLUE);
		area.setOpacity(0.4);
	}

	@Override
	public Node getNode() {
		return area;
	}

}

class HlcPriceUnitTypeButtonGraphic implements PriceUnitTypeButtonGraphic {

	private final Line line2 = new Line(0, 0, 0, 10);
	private final Line line3 = new Line(0, 3, 5, 3);
	private final Group hlc = new Group(line2, line3);

	public HlcPriceUnitTypeButtonGraphic() {
		hlc.getStyleClass().add("unit-type-graphic");
		line2.setStroke(Color.GREEN);
		line2.setStrokeWidth(2);
		line3.setStroke(Color.GREEN);
		line3.setStrokeWidth(2);
	}

	@Override
	public Node getNode() {
		return hlc;
	}

}

class OhlcPriceUnitTypeButtonGraphic implements PriceUnitTypeButtonGraphic {

	private final Line line1 = new Line(0, 7, 5, 7);
	private final Line lineO = new Line(5, 0, 5, 10);
	private final Line lineC = new Line(5, 3, 10, 3);
	private final Group ohlc = new Group(line1, lineO, lineC);

	public OhlcPriceUnitTypeButtonGraphic() {
		ohlc.getStyleClass().add("unit-type-graphic");
	}

	@Override
	public Node getNode() {
		return ohlc;
	}

}
