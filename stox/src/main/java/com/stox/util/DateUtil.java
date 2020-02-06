package com.stox.util;

import java.util.Date;

public class DateUtil {
	
	@SuppressWarnings("deprecation")
	public static Date trim(Date date){
		date.setSeconds(0);
		date.setMinutes(0);
		date.setHours(0);
		return date;
	}

}
