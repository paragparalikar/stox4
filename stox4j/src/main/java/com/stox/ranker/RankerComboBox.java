package com.stox.ranker;

import javafx.scene.control.ComboBox;

public class RankerComboBox extends ComboBox<Ranker<?>> {

	public RankerComboBox() {
		getItems().add(new VolatilityContractionRanker());
	}
	
}
