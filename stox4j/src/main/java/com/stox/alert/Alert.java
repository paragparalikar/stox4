package com.stox.alert;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = "isin")
public class Alert {

	private final String isin;
	private final double price;
	private boolean satisfied;

}
