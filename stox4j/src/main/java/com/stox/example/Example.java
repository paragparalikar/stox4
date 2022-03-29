package com.stox.example;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Example implements Serializable {
	private static final long serialVersionUID = 2896735300761747437L;

	private String isin;
	private String groupId;
	private ZonedDateTime timestamp;
	
}
