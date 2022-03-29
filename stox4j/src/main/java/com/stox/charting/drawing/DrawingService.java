package com.stox.charting.drawing;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class DrawingService {

	@Delegate
	private final DrawingRepository repository;
	
}
