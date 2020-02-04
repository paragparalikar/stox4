package com.stox.fx.fluent.stage;

import javafx.stage.Window;

public class FluentWindow extends Window implements IFluentWindow<FluentWindow> {

	@Override
	public FluentWindow getThis() {
		return this;
	}

}
