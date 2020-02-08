package com.stox.util;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuple2<K, V> {
	
	public static <K,V>  Function<K, Tuple2<K,V>> map(@NonNull final Function<K,V> valueExtractor){
		return t -> new Tuple2<>(t, valueExtractor.apply(t));
	}

	private K key;
	
	private V value;
	
}
