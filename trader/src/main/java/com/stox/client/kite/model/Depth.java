package com.stox.client.kite.model;

import lombok.Data;

@Data
public class Depth {

	private double price;
	private int quantity;
	private int orders;
	
}
