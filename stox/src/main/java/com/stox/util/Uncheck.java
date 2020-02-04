package com.stox.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.SneakyThrows;

public interface Uncheck {
	
	interface UncheckedConsumer<T>{
		void consume(T object) throws Exception;
	}
	
	public static <T> Consumer<T> consumer(UncheckedConsumer<T> consumer){
		return new Consumer<T>() {
			@Override
			@SneakyThrows
			public void accept(T object) {
				consumer.consume(object);
			}
		};
	}
	
	interface UncheckedSupplier<T>{
		T get() throws Exception; 
	}
	
	public static <T> Supplier<T> supplier(UncheckedSupplier<T> supplier){
		return new Supplier<T>(){
			@Override
			@SneakyThrows
			public T get() {
				return supplier.get();
			}
		};
	}
	
	interface UncheckedPredicate<T>{
		boolean test(T object) throws Exception;
	}
	
	public static <T> Predicate<T> predicate(UncheckedPredicate<T> predicate){
		return new Predicate<T>() {
			@Override
			@SneakyThrows
			public boolean test(T object) {
				return predicate.test(object);
			}
		};
	}
	
	interface UncheckedFunction<T,V>{
		V apply(T object) throws Exception;
	}
	
	public static <T,V> Function<T,V> function(UncheckedFunction<T,V> function){
		return new Function<T,V>(){
			@Override
			@SneakyThrows
			public V apply(T object) {
				return function.apply(object);
			}
		};
	}
	
	interface UncheckedRunnable{
		void run() throws Exception;
	}
	
	public static Runnable runnable(UncheckedRunnable runnable) {
		return new Runnable() {
			@Override
			@SneakyThrows
			public void run() {
				runnable.run();
			}
		};
	}
	
}
