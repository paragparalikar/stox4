package com.stox.common.persistence;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class SerializationService {

	@Delegate
	private final SerializationRepository repository;
	
}
