package com.stox.example;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExampleGroup implements Serializable {
	private static final long serialVersionUID = -3671134427224568589L;

	private String id;
	private String name;
	
	@Override
	public String toString() {
		return name;
	}
	
}
