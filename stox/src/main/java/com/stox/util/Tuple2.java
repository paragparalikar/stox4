package com.stox.util;

import lombok.Data;

@Data
public class Tuple2<K, V> {

	private K key;
	
	private V value;
	
}
