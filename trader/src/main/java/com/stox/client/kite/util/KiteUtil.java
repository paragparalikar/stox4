package com.stox.client.kite.util;

import java.time.Duration;

import com.stox.client.kite.KiteClient;
import com.stox.client.kite.KiteCredentials;
import com.stox.client.kite.KiteSession;
import com.stox.client.kite.adapter.KiteBarRepository;
import com.stox.client.kite.adapter.KiteBarService;
import com.stox.client.kite.adapter.KiteDataProvider;
import com.stox.client.kite.adapter.KiteDeltaBarDownloader;
import com.stox.client.kite.adapter.KiteScripService;
import com.stox.common.bar.Interval;
import com.stox.data.DataProvider;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KiteUtil {
	
	public DataProvider createDataProvider(KiteCredentials kiteCredentials) {
		final KiteSession kiteSession = new KiteSession(kiteCredentials);
		final KiteClient kiteClient = new KiteClient(kiteSession);
		final KiteBarRepository kiteBarRepository = new KiteBarRepository();
		final KiteDeltaBarDownloader kiteDeltaBarDownloader = new KiteDeltaBarDownloader(
				kiteClient, kiteBarRepository);
		final KiteBarService kiteBarService = new KiteBarService(kiteDeltaBarDownloader);
		final KiteScripService kiteScripService = new KiteScripService();
		return new KiteDataProvider(kiteBarService, kiteScripService);
	}

	public Interval toInterval(String kiteInterval) {
		switch (kiteInterval) {
		case KiteConstant.INTERVAL_MINUTE:
			return Interval.MINUTE;
		case KiteConstant.INTERVAL_3_MINUTE:
			return Interval.MINUTE3;
		case KiteConstant.INTERVAL_5_MINUTE:
			return Interval.MINUTE5;
		case KiteConstant.INTERVAL_10_MINUTE:
			return Interval.MINUTE10;
		case KiteConstant.INTERVAL_15_MINUTE:
			return Interval.MINUTE15;
		case KiteConstant.INTERVAL_30_MINUTE:
			return Interval.MINUTE30;
		case KiteConstant.INTERVAL_60_MINUTE:
			return Interval.MINUTE60;
		case KiteConstant.INTERVAL_2_HOUR:
			return Interval.HOUR2;
		case KiteConstant.INTERVAL_3_HOUR:
			return Interval.HOUR3;
		case KiteConstant.INTERVAL_DAY:
			return Interval.DAY;
		default:
			return null;
		}
	}
	
	public String toKiteInterval(Interval interval) {
		switch(interval) {
		case DAY:
			return KiteConstant.INTERVAL_DAY;
		case HOUR2:
			return KiteConstant.INTERVAL_2_HOUR;
		case HOUR3:
			return KiteConstant.INTERVAL_3_HOUR;
		case MINUTE:
			return KiteConstant.INTERVAL_MINUTE;
		case MINUTE10:
			return KiteConstant.INTERVAL_10_MINUTE;
		case MINUTE15:
			return KiteConstant.INTERVAL_15_MINUTE;
		case MINUTE3:
			return KiteConstant.INTERVAL_3_MINUTE;
		case MINUTE30:
			return KiteConstant.INTERVAL_30_MINUTE;
		case MINUTE5:
			return KiteConstant.INTERVAL_5_MINUTE;
		case MINUTE60:
			return KiteConstant.INTERVAL_60_MINUTE;
		default:
			return null;
		}
	}
	
	public Duration getMaxIncrement(String kiteInterval) {
		switch (kiteInterval) {
		case KiteConstant.INTERVAL_MINUTE:
			return Duration.ofDays(60);
		case KiteConstant.INTERVAL_3_MINUTE:
			return Duration.ofDays(100);
		case KiteConstant.INTERVAL_5_MINUTE:
			return Duration.ofDays(100);
		case KiteConstant.INTERVAL_10_MINUTE:
			return Duration.ofDays(100);
		case KiteConstant.INTERVAL_15_MINUTE:
			return Duration.ofDays(200);
		case KiteConstant.INTERVAL_30_MINUTE:
			return Duration.ofDays(200);
		case KiteConstant.INTERVAL_60_MINUTE:
			return Duration.ofDays(400);
		case KiteConstant.INTERVAL_2_HOUR:
			return Duration.ofDays(400);
		case KiteConstant.INTERVAL_3_HOUR:
			return Duration.ofDays(400);
		case KiteConstant.INTERVAL_DAY:
			return Duration.ofDays(2000);
		default:
			return null;
		}
	}

}
