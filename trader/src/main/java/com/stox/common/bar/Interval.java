package com.stox.common.bar;

import java.time.Duration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Interval {

	MINUTE(Duration.ofMinutes(1)), 
	MINUTE3(Duration.ofMinutes(3)), 
	MINUTE5(Duration.ofMinutes(5)), 
	MINUTE10(Duration.ofMinutes(10)), 
	MINUTE15(Duration.ofMinutes(15)), 
	MINUTE30(Duration.ofMinutes(30)), 
	MINUTE60(Duration.ofMinutes(60)), 
	HOUR2(Duration.ofHours(1)), 
	HOUR3(Duration.ofHours(3)), 
	DAY(Duration.ofDays(1)),;
	
	@Getter private final Duration duration;
	
}
