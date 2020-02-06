package com.stox;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.NumberFormat;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Config {

	@NonNull
	private final Path home;
	
	@NonNull
	private final DateFormat dateFormat;
	
	@NonNull
	private final DateFormat dateFormatFull;
	
	@NonNull
	private final NumberFormat currencyFormat;

}
