package com.stox.module.charting;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Configuration {

	private final ObjectProperty<Color> chartBackgroundColorProperty = new SimpleObjectProperty<>(Color.WHITE);

	private final ObjectProperty<Color> wickColorProperty = new SimpleObjectProperty<>(Color.web("#454545"));

	private final DoubleProperty wickWidthProperty = new SimpleDoubleProperty(1);

	private final ObjectProperty<Color> upBarColorProperty = new SimpleObjectProperty<>(Color.web("#5cb85c"));

	private final ObjectProperty<Color> upBarBorderColorProperty = new SimpleObjectProperty<>(Color.web("#5cb85c"));

	private final DoubleProperty upBarBorderWidthProperty = new SimpleDoubleProperty(1);

	private final ObjectProperty<Color> downBarColorProperty = new SimpleObjectProperty<>(Color.web("#d9534f"));

	private final ObjectProperty<Color> downBarBorderColorProperty = new SimpleObjectProperty<>(Color.web("#d9534f"));

	private final DoubleProperty downBarBorderWidthProperty = new SimpleDoubleProperty(1);

	private final ObjectProperty<Color> volumeColorProperty = new SimpleObjectProperty<>(Color.GRAY);

	private final DoubleProperty volumeOpacityProperty = new SimpleDoubleProperty(1);
	
	private final IntegerProperty rightGapBarCountProperty = new SimpleIntegerProperty(5);

	/* Derived values */

	private Background upBarBackground = new Background(new BackgroundFill(upBarColorProperty.get(), null, null));

	private Border upBarBorder = new Border(new BorderStroke(upBarBorderColorProperty.get(), null, null, new BorderWidths(upBarBorderWidthProperty.get())));

	private Background downBarBackground = new Background(new BackgroundFill(downBarColorProperty.get(), null, null));

	private Border downBarBorder = new Border(new BorderStroke(downBarBorderColorProperty.get(), null, null, new BorderWidths(downBarBorderWidthProperty.get())));

	public Configuration() {
		upBarColorProperty.addListener((o, old, color) -> upBarBackground = new Background(new BackgroundFill(color, null, null)));
		downBarColorProperty.addListener((o, old, color) -> downBarBackground = new Background(new BackgroundFill(color, null, null)));
		upBarBorderColorProperty.addListener((o, old, value) -> updateUpBarBoder());
		upBarBorderWidthProperty.addListener((o, old, value) -> updateUpBarBoder());
		downBarBorderColorProperty.addListener((o, old, value) -> updateDownBarBoder());
		downBarBorderWidthProperty.addListener((o, old, value) -> updateDownBarBoder());
	}

	private void updateUpBarBoder() {
		upBarBorder = new Border(new BorderStroke(upBarBorderColorProperty.get(), null, null, new BorderWidths(upBarBorderWidthProperty.get())));
	}

	private void updateDownBarBoder() {
		downBarBorder = new Border(new BorderStroke(downBarBorderColorProperty.get(), null, null, new BorderWidths(downBarBorderWidthProperty.get())));
	}

}
