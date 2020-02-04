package com.stox.fx.fluent.stage;

import javafx.stage.Stage;

public class FluentStage extends Stage implements IFluentStage<FluentStage> {

	@Override
	public FluentStage getThis() {
		return this;
	}

}
