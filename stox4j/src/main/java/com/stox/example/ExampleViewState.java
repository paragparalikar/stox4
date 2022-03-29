package com.stox.example;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExampleViewState implements Serializable {
	private static final long serialVersionUID = 9070486418571547367L;

	private Example selectedItem;
	private Example firstVisibleItem;
	private ExampleGroup selectedGroup;
	
	
}
