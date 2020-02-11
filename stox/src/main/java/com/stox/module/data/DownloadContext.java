package com.stox.module.data;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import com.stox.fx.widget.FxMessageSource;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ExchangeRepository;
import com.stox.module.core.persistence.ScripRepository;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DownloadContext {
	
	private boolean cancelled;

	private final Exchange exchange;
	
	private final Runnable afterCallback;
	
	private final Calendar lastDownloadDate;
	
	private final long initialLastDownloadDate;
	
	private final Runnable terminationCallback;
	
	private final ExecutorService executorService;
	
	private final Consumer<StatusMessage> messageCallback;
	
	private final ExchangeRepository exchangeRepository;
	
	private final ScripRepository scripRepository;

	private final BarRepository barRepository;
	
	private final FxMessageSource messageSource;
	
}
