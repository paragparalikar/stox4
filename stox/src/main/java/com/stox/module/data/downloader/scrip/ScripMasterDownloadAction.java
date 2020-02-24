package com.stox.module.data.downloader.scrip;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.Action;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.DateUtil;

import lombok.Builder;

@Builder
public class ScripMasterDownloadAction implements Action {

	private final Exchange exchange;
	private final ScripRepository scripRepository;
	private final Runnable before, success, failure;

	@Override
	public boolean validate() {
		return Optional.ofNullable(scripRepository.getLastModifiedDate(exchange)).orElseGet(() -> new Date(0)).before(DateUtil.trim(new Date()));
	}

	@Override
	public void before() {
		Optional.ofNullable(before).ifPresent(Runnable::run);
	}

	@Override
	public void execute() throws Throwable {
		final ScripMasterDownloaderFactory factory = new ScripMasterDownloaderFactory();
		final ScripMasterDownloader downloader = factory.create(exchange);
		final List<Scrip> scrips = downloader.download(exchange);
		scripRepository.save(exchange, scrips);
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
