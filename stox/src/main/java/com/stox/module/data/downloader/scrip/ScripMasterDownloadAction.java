package com.stox.module.data.downloader.scrip;

import java.util.Date;
import java.util.List;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.Action;
import com.stox.module.data.DownloadContext;
import com.stox.module.data.StatusMessage;
import com.stox.module.data.downloader.bar.eod.EodBarDownloadAction;
import com.stox.util.DateUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScripMasterDownloadAction implements Action {

	private StatusMessage message;
	private final DownloadContext context;

	@Override
	public boolean validate() {
		final Date date = context.getScripRepository().getLastModifiedDate(context.getExchange());
		return date.before(DateUtil.trim(new Date())) && !context.isCancelled();
	}

	@Override
	public void before() {
		message = new StatusMessage(context.getMessageSource().get("Download scrip master"));
		context.getMessageCallback().accept(message);
	}

	@Override
	public void execute() throws Throwable {
		final Exchange exchange = context.getExchange();
		final ScripMasterDownloaderFactory factory = new ScripMasterDownloaderFactory();
		final ScripMasterDownloader downloader = factory.create(exchange);
		final List<Scrip> scrips = downloader.download(exchange);
		context.getScripRepository().save(exchange, scrips);
	}

	@Override
	public void success() {
		message.success(true);
	}

	@Override
	public void failure(Throwable throwable) {
		message.success(false);
		throwable.printStackTrace();
	}

	@Override
	public void after() {
		context.getAfterCallback().run();
		if (context.isCancelled()) {
			context.getTerminationCallback().run();
		}else{
			context.getExecutorService().submit(new EodBarDownloadAction(context));
		}
	}

}
