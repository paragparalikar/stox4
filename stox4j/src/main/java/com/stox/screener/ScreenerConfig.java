package com.stox.screener;

import lombok.Data;

public interface ScreenerConfig {
	
	@Data
	public static class FixedBarCountScreenerConfig implements ScreenerConfig {
		private final int barCount;
	}

	int getBarCount();
	
}
