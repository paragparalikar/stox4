package com.stox.common.util.function;

import java.util.function.Predicate;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Predicates {

	public static <T extends Number> Predicate<T> greaterThan(Number number){
		return value -> value.doubleValue() > number.doubleValue();
	}
	
	public static <T extends Number> Predicate<T> greaterThanOrEquals(Number number){
		return value -> value.doubleValue() >= number.doubleValue(); 
	}
	
	public static <T extends Number> Predicate<T> lessThan(Number number){
		return value -> value.doubleValue() < number.doubleValue();
	}
	
	public static <T extends Number> Predicate<T> lessThanOrEquals(Number number){
		return value -> value.doubleValue() <= number.doubleValue();
	}
}
