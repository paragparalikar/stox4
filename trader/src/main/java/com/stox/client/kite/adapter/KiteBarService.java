package com.stox.client.kite.adapter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.stox.common.bar.Bar;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class KiteBarService {

	private final KiteBarRepository kiteBarRepository;
	private final KiteDeltaBarDownloader kiteDeltaBarDownloader;
	
	public List<Bar> read(int instrumentToken, String interval){
		download(instrumentToken, interval);
		log.info("Loading all bars from local repo, this may take some time...");
		return kiteBarRepository.read(instrumentToken, interval);
	}	
	
	public void download(int instrumentToken, String interval){
		final ZonedDateTime limit = Optional.ofNullable(kiteBarRepository.getLastBarEndTime(instrumentToken, interval))
				.orElseGet(() -> ZonedDateTime.now().minus(Duration.ofDays(10000)));
		log.info("Limit for download : {}", limit);
		final List<Bar> downloadedBars = kiteDeltaBarDownloader.download(instrumentToken, interval, limit);
		log.info("Downloaded {} bars", downloadedBars.size());
		if(null != downloadedBars && !downloadedBars.isEmpty()) kiteBarRepository.write(instrumentToken, interval, downloadedBars);
	}
}
