package com.stox.util.function;

import java.util.function.Consumer;

import lombok.NonNull;

public interface ThrowingConsumer<T> {

	public static <T> Consumer<T> from(@NonNull final ThrowingConsumer<T> throwingConsumer){
		return t -> {
			try {
				throwingConsumer.accept(t);
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
	
	void accept(T t) throws Exception;
	
}
