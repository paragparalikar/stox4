package com.stox.example.event;

import com.stox.example.Example;

import lombok.Value;

@Value
public class ExampleRemovedEvent {

	private Example example;
	
}
