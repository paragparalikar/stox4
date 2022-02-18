package com.stox.common.data;

import java.time.ZonedDateTime;
import java.util.List;

import org.ta4j.core.Bar;

import com.stox.common.bar.BarService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileSystemDataProvider implements DataProvider {

	private final BarService barService;
	private final ScripService scripService;
	
	@Override
	public List<Scrip> findAll() {
		return scripService.findAll();
	}

	@Override
	public List<Bar> findByIsin(String isin, int count) {
		return barService.find(isin, count);
	}

	@Override
	public List<Bar> findByIsin(String isin, int count, ZonedDateTime offset) {
		return barService.find(isin, count, offset);
	}

}
