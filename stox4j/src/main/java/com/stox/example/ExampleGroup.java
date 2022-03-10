package com.stox.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleGroup {

	private String id;
	private String name;
	
	@Override
	public String toString() {
		return name;
	}
	
}
