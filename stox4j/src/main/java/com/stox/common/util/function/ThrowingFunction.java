package com.stox.common.util.function;

import java.util.function.Function;

public interface ThrowingFunction<T, R> {
	
	public static <T, R> Function<T, R> from(ThrowingFunction<T, R> throwingFunction){
		return t -> {
			try {
				return throwingFunction.apply(t);
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	R apply(T t) throws Exception;

}
