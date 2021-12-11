package com.stox.module.screen;

import java.util.Arrays;
import java.util.List;

import com.stox.module.core.model.Bar;

import lombok.NonNull;


public interface Screen<T> {
	public static final List<Screen<?>> ALL = Arrays.asList(
			new BhedaScreen(),
			new BullishSqueezeScreen(),
			new BullishEntryBarScreen(), 
			new BullishPriceReversalScreen());
	
	public static <T> Screen<T> ofType(@NonNull final Class<Screen<T>> type){
		return ALL.stream().filter(type::isInstance).map(type::cast).findFirst().orElse(null);
	}

	String code();
	
	String name();
	
	ScreenType screenType();
	
	T defaultConfig();
	
	int minBarCount(final T config);
	
	boolean match(final List<Bar> bars, final T config);
	
}
