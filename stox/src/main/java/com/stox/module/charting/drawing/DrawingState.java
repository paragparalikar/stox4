package com.stox.module.charting.drawing;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public abstract class DrawingState {

	@NonNull
	private final String type;
	
	public abstract Drawing<?> drawing();
	
}
