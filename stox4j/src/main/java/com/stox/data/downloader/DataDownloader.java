package com.stox.data.downloader;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import org.greenrobot.eventbus.EventBus;
import org.ta4j.core.Bar;

import com.stox.common.bar.BarService;
import com.stox.common.event.MessageEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.data.downloader.bar.EodBarDownloader;
import com.stox.data.downloader.scrip.ScripMasterDownloader;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Builder
@RequiredArgsConstructor
public class DataDownloader {

	private final Executor executor;
	private final EventBus eventBus;
	private final BarService barService;
	private final ScripService scripService;
	private final EodBarDownloader eodBarDownloader;
	private final ScripMasterDownloader scripMasterDownloader;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	
	public void download() {
		executor.execute(() -> {
			downloadScrips();
			downloadBars();
		});
		eventBus.post(MessageEvent.builder()
				.icon(Icon.DOWNLOAD)
				.style("success")
				.text("Downloading data...")
				.build());
	}
	
	@SneakyThrows
	private void downloadBars() {
		ZonedDateTime lastDownloadDate = barService.readLastDownloadDate();
		while(lastDownloadDate.isBefore(ZonedDateTime.now())) {
			lastDownloadDate = lastDownloadDate.plusDays(1);
			final Map<Scrip, Bar> data = eodBarDownloader.download(lastDownloadDate);
			final CountDownLatch latch = new CountDownLatch(data.size());
			data.forEach((scrip, bar) -> executor.execute(() -> {
				try { barService.save(scrip.getIsin(), bar); } finally { latch.countDown(); }
			}));
			latch.await();
			if(!data.isEmpty()) barService.writeLastDownloadDate(lastDownloadDate);
			eventBus.post(MessageEvent.builder()
					.icon(Icon.DOWNLOAD)
					.style("success")
					.text(String.format("Downloaded %d bars for %s", 
							data.size(), formatter.format(lastDownloadDate)))
					.build());
		}
		eventBus.post(MessageEvent.builder()
				.icon(Icon.DOWNLOAD)
				.style("success")
				.text(String.format("Last downloaded bar data on %s", 
						DateTimeFormatter.ISO_DATE.format(barService.readLastDownloadDate())))
				.build());
	}
	
	@SneakyThrows
	private void downloadScrips() {
		final ZonedDateTime lastModifiedDate = scripService.getLastModifiedDate();
		if(null == lastModifiedDate || lastModifiedDate.isBefore(LocalDate.now().atStartOfDay(ZoneId.systemDefault()))) {
			final List<Scrip> scrips = scripMasterDownloader.download();
			scripService.saveAll(scrips);
			eventBus.post(MessageEvent.builder()
					.icon(Icon.DOWNLOAD)
					.style("success")
					.text(String.format("Downloaded %d scrips", scrips.size()))
					.build());
		}
	}
	
}
