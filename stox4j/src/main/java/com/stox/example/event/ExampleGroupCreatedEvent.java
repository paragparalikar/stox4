package com.stox.example.event;

import com.stox.example.ExampleGroup;

import lombok.Value;

@Value
public class ExampleGroupCreatedEvent {

	private final ExampleGroup exampleGroup;
	
}
