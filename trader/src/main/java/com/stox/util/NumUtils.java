package com.stox.util;

import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumUtils {

	public Num valueOf(String text) { 
		return null != text && !text.trim().isEmpty() && !"null".equalsIgnoreCase(text) ? 
				DoubleNum.valueOf(text) : null;
	}
	
}
