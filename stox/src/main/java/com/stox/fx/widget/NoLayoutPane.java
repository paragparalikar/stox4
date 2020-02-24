package com.stox.fx.widget;

import com.stox.fx.fluent.scene.layout.FluentPane;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false, of = { "mutex" })
public class NoLayoutPane extends FluentPane {

	private final Object mutex = new Object();

	@Override
	protected void layoutChildren() {

	}

}
