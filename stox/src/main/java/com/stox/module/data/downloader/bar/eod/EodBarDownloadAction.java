package com.stox.module.data.downloader.bar.eod;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.intf.Action;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ExchangeRepository;
import com.stox.module.core.persistence.ScripRepository;

import lombok.Builder;

@Builder
public class EodBarDownloadAction implements Action {
	
	private final Date date;
	private final Exchange exchange;
	private final ExecutorService executorService;
	private final BarRepository barRepository;
	private final ScripRepository scripRepository;
	private final ExchangeRepository exchangeRepository;
	private final Runnable before, success, failure;

	@Override
	public boolean validate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Calendar.SATURDAY != calendar.get(Calendar.DAY_OF_WEEK)
				&& Calendar.SUNDAY != calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	@Override
	public void before() {
		Optional.ofNullable(before).ifPresent(Runnable::run);
	}
	
	@Override
	public void execute() throws Throwable {
		final EodBarDownloaderFactory eodBarDownloaderFactory = new EodBarDownloaderFactory();
		final EodBarDownloader eodBarDownloader = eodBarDownloaderFactory.create(exchange, scripRepository);
		persist(eodBarDownloader.download(date));
		exchangeRepository.writeLastDownloadDate(exchange, date);
	}
	
	private void persist(final List<Bar> bars) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(bars.size());
		bars.forEach(bar -> executorService.submit(() -> {
			try {
				barRepository.save(bar, BarSpan.D);
			} finally {
				latch.countDown();
			}
		}));
		latch.await();
	}
	
	@Override
	public void success() {
		Optional.ofNullable(success).ifPresent(Runnable::run);
	}

	@Override
	public void failure(Throwable throwable) {
		Optional.ofNullable(failure).ifPresent(Runnable::run);
	}
	
}
