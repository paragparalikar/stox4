package com.stox;

import java.util.List;

import com.stox.client.kite.KiteCredentials;
import com.stox.client.kite.util.KiteUtil;
import com.stox.common.bar.Bar;
import com.stox.common.bar.Interval;
import com.stox.data.DataProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

	public static void main(String[] args) {
		final DataProvider dataProvider = KiteUtil.createDataProvider(
				KiteCredentials.builder()
				
				.build());
		final List<Bar> bars = dataProvider.findBars("256265", Interval.MINUTE5);
		log.info("Got {} bars", bars.size());
	}
	
	
	
}
