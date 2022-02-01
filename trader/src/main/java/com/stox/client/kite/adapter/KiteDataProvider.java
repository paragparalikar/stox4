package com.stox.client.kite.adapter;

import java.time.ZonedDateTime;
import java.util.List;

import com.stox.client.kite.util.KiteUtil;
import com.stox.common.bar.Bar;
import com.stox.common.bar.Interval;
import com.stox.common.scrip.Scrip;
import com.stox.data.DataProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KiteDataProvider implements DataProvider {
	
	private final KiteBarService kiteBarService;
	private final KiteScripService kiteScripService;
	
	@Override
	public List<Scrip> findAllScrips() {
		return kiteScripService.findAllScrips();
	}

	@Override
	public Scrip findScripByIsin(String isin) {
		return kiteScripService.findScripByIsin(isin);
	}

	@Override
	public List<Bar> findBars(String isin, Interval interval) {
		return kiteBarService.read(Integer.parseInt(isin), 
				KiteUtil.toKiteInterval(interval));
	}
	
	@Override
	public List<Bar> findBars(String isin, Interval interval, int count) {
		return null;
	}

	@Override
	public List<Bar> findBars(String isin, Interval interval, ZonedDateTime from, ZonedDateTime to) {
		return null;
	}

}
