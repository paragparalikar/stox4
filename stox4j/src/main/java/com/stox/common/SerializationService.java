package com.stox.common;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class SerializationService {

	@Delegate
	private final SerializationRepository repository;
	
}
