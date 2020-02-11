package com.stox.module.data.downloader.bar.eod;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.intf.Action;
import com.stox.module.data.DownloadContext;
import com.stox.module.data.StatusMessage;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EodBarDownloadAction implements Action {
	private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM);

	private StatusMessage message;
	private final DownloadContext context;

	@Override
	public boolean validate() {
		final Calendar lastDownloadDate = context.getLastDownloadDate();
		return !context.isCancelled() && Calendar.SATURDAY != lastDownloadDate.get(Calendar.DAY_OF_WEEK)
				&& Calendar.SUNDAY != lastDownloadDate.get(Calendar.DAY_OF_WEEK);
	}

	@Override
	public void before() {
		final Calendar lastDownloadDate = context.getLastDownloadDate();
		final ObservableValue<String> textValue = context.getMessageSource().get("Download EOD bars for ");
		message = new StatusMessage(new StringBinding() {
			{bind(textValue);}
			@Override
			protected String computeValue() {
				return textValue.getValue() + DATE_FORMAT.format(lastDownloadDate.getTime());
			}
		});
		context.getMessageCallback().accept(message);
	}

	@Override
	public void execute() throws Throwable {
		final Exchange exchange = context.getExchange();
		final Calendar lastDownloadDate = context.getLastDownloadDate();
		final EodBarDownloaderFactory eodBarDownloaderFactory = new EodBarDownloaderFactory(context.getScripRepository());
		final EodBarDownloader eodBarDownloader = eodBarDownloaderFactory.create(exchange);
		final List<Bar> bars = eodBarDownloader.download(lastDownloadDate.getTime());
		persist(bars);
	}
	
	private void persist(List<Bar> bars) throws InterruptedException{
		final CountDownLatch latch = new CountDownLatch(bars.size());
		bars.forEach(bar -> context.getExecutorService().submit(() -> {
			try{
				context.getBarRepository().save(bar, BarSpan.D);
			}finally{
				latch.countDown();
			}
		}));
		latch.await();
	}

	@Override
	public void success() {
		final Exchange exchange = context.getExchange();
		final Calendar lastDownloadDate = context.getLastDownloadDate();
		context.getExchangeRepository().writeLastDownloadDate(exchange, lastDownloadDate.getTime());
		message.success(Boolean.TRUE);
	}

	@Override
	public void failure(Throwable throwable) {
		message.success(Boolean.FALSE);
	}

	@Override
	public void after() {
		context.getAfterCallback().run();
		final Calendar lastDownloadDate = context.getLastDownloadDate();
		lastDownloadDate.add(Calendar.DATE, 1);
		if (context.isCancelled() || lastDownloadDate.after(Calendar.getInstance())) {
			context.getTerminationCallback().run();
		} else {
			final EodBarDownloadAction action = new EodBarDownloadAction(context);
			context.getExecutorService().submit(action);
		}
	}

}
