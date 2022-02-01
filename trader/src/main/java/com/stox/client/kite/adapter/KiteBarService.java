package com.stox.client.kite.adapter;

import java.util.List;

import com.stox.common.bar.Bar;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KiteBarService {

	private final KiteDeltaBarDownloader kiteDeltaBarDownloader;
	
	public List<Bar> read(int instrumentToken, String interval){
		return kiteDeltaBarDownloader.download(instrumentToken, interval);
	}	
}
