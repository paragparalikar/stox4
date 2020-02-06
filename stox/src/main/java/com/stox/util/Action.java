package com.stox.util;

public interface Action extends Runnable {

	public void execute() throws Throwable;

	public default void run() {
		try {
			if (validate()) {
				before();
				execute();
				success();
			}
		} catch (Throwable throwable) {
			failure(throwable);
		} finally {
			after();
		}
	}

	public default boolean validate() {
		return true;
	}

	public default void before() {

	}

	public default void success() {

	}

	public default void failure(Throwable throwable) {
		throwable.printStackTrace();
	}

	public default void after() {

	}

}
