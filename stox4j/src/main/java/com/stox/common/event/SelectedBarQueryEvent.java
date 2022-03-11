package com.stox.common.event;

import org.ta4j.core.Bar;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SelectedBarQueryEvent extends SelectedScripQueryEvent {

	private Bar bar;
	private final double screenX, screenY;
	
}
