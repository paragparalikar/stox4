package com.stox.alert;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(of = "isin")
public class Alert {

	private final String isin;
	private final double price;
	
}
