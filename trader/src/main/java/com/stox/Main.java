package com.stox;

import com.stox.client.kite.KiteCredentials;
import com.stox.client.kite.util.KiteUtil;
import com.stox.data.DataProvider;

public class Main {

	public static void main(String[] args) {
		final DataProvider dataProvider = KiteUtil.createDataProvider(
				KiteCredentials.builder()
				.pin("123456")
				.username("YS9709")
				.password("Dark@Horse1")
				.build());
		
		
	}
	
	
	
}
