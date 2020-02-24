package com.stox.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

public class StringUtil {
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	{
		currencyFormat.setGroupingUsed(true);
		currencyFormat.setMaximumFractionDigits(2);
		currencyFormat.setMinimumFractionDigits(0);	
	}

	public static boolean hasText(String text) {
		return null != text && 0 < text.trim().length();
	}

	public static void ifHasText(String[] tokens, int index, Consumer<String> consumer) {
		if (null != tokens && null != consumer && 0 <= index && index < tokens.length && hasText(tokens[index])) {
			consumer.accept(tokens[index]);
		}
	}

	public static String toString(InputStream stream) {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.lines().collect(Collectors.joining(System.lineSeparator()));
	}

	public static String toString(ZipInputStream inStream) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(102400);
		final byte[] buffer = new byte[100 * 1024];
		if (inStream.getNextEntry() != null) {
			int nrBytesRead = 0;
			while ((nrBytesRead = inStream.read(buffer)) > 0) {
				baos.write(buffer, 0, nrBytesRead);
			}
		}
		inStream.close();
		return new String(baos.toByteArray());
	}

	public static String splitCamelCase(String s) {
		s = s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String toDebugString(final Throwable throwable) {
		final Throwable rootThrowable = getCause(throwable);
		final StringBuilder stringBuilder = new StringBuilder("Exception : " + rootThrowable.getClass().getName());
		final StackTraceElement[] stackTraceElements = rootThrowable.getStackTrace();
		if (null != stackTraceElements && 0 < stackTraceElements.length) {
			final StackTraceElement stackTraceElement = stackTraceElements[0];
			stringBuilder.append(System.lineSeparator() + stackTraceElement.getClassName());
			stringBuilder.append("." + stackTraceElement.getMethodName());
			stringBuilder.append(System.lineSeparator() + " at line " + stackTraceElement.getLineNumber());
			stringBuilder.append(" in " + stackTraceElement.getFileName());
		}
		return stringBuilder.toString();
	}

	public static String toString(final Throwable throwable) {
		final StringJoiner joiner = new StringJoiner("\n");
		joiner.add(throwable.getMessage());
		Throwable cause = throwable.getCause();
		while (null != cause) {
			joiner.add(cause.getMessage());
			cause = cause.getCause();
		}
		return joiner.toString();
	}

	private static Throwable getCause(final Throwable throwable) {
		return null == throwable.getCause() ? throwable : getCause(throwable);
	}
	
	
	public static String stringValueOf(double value) {
		String text = "";
		if (value >= 1000000000) {
			text = "B";
			value /= 1000000000;
		} else if (value >= 1000000) {
			text = "M";
			value /= 1000000;
		} else if (value >= 1000) {
			text = "K";
			value /= 1000;
		}
		return String.valueOf(currencyFormat.format(value) + text);
	}

}
