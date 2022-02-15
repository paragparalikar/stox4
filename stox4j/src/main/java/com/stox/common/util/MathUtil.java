package com.stox.common.util;

public class MathUtil {

	public static double praportion(final double mina, final double a, final double maxa, final double minb,
			final double maxb) {
		return minb + (((a - mina) * (maxb - minb)) / (maxa - mina));
	}

	public static double ceil(double value, double boxSize) {
		return Math.ceil(value / boxSize) * boxSize;
	}

	public static double floor(double value, double boxSize) {
		return Math.floor(value / boxSize) * boxSize;
	}

	public static long clip(final long min, final long value, final long max) {
		return Math.max(min, Math.min(max, value));
	}

	public static int clip(int min, int value, int max) {
		return Math.max(min, Math.min(max, value));
	}

	public static double trim(double value, int decimalPlaces) {
		final double multiple = Math.pow(10, decimalPlaces);
		return Math.floor(value * multiple) / multiple;
	}
	
	public static double round(final double value, final double fraction) {
		final double multiple = 1/fraction;
		final double ceil = Math.ceil(value * multiple) / multiple;
		final double floor = Math.floor(value * multiple) / multiple;
		return ceil - value < value - floor ? ceil : floor;
	}

	public static int round(final double value) {
		final double ceil = Math.ceil(value);
		final double floor = Math.floor(value);
		return (int) (ceil - value < value - floor ? ceil : floor);
	}

}
