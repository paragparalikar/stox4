package com.stox.common.quote;

import com.stox.common.scrip.Scrip;

import lombok.Data;

@Data
public class Quote {

	private final Scrip scrip;
	private final double ltp;

}
