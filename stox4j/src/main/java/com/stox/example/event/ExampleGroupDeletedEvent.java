package com.stox.example.event;

import com.stox.example.ExampleGroup;

import lombok.Value;

@Value
public class ExampleGroupDeletedEvent {

	private final ExampleGroup exampleGroup;
	
}
