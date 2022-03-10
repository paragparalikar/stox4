package com.stox.example;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Example {

	private String isin;
	private String groupId;
	private ZonedDateTime timestamp;
	
}
