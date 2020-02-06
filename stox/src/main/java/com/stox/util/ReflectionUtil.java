package com.stox.util;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class ReflectionUtil {

	public static Field findField(String name, Class<?> target) {
		Class<?> type = target;
		while (!Object.class.equals(type)) {
			try {
				return type.getDeclaredField(name);
			} catch (NoSuchFieldException ignored) {
			}
		}
		return null;
	}

	public static void fields(Class<?> type, Consumer<Field> consumer) {
		while (!Object.class.equals(type)) {
			for (final Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				consumer.accept(field);
			}
			type = type.getSuperclass();
		}
	}

}
