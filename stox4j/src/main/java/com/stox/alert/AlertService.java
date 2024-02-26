package com.stox.alert;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.greenrobot.eventbus.EventBus;

import com.stox.alert.event.AlertSatisfiedEvent;
import com.stox.common.quote.Quote;
import com.stox.common.quote.QuoteService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.util.Audio;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class AlertService {

	private final EventBus eventBus;
	private final ScripService scripService;
	private final QuoteService quoteService;
	@Delegate private final AlertRepository alertRepository;

	public void pollAsync() {
		Thread.ofVirtual().start(this::poll);
	}
	
	@SneakyThrows
	public void poll() {
		final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
		final List<Alert> unsatisfiedAlerts = alertRepository.findAll().stream()
			.filter(alert -> !alert.isSatisfied())
			.toList();
		unsatisfiedAlerts.forEach(alert -> executorService.submit(() -> alert.setSatisfied(isSatisfied(alert))));
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
		if(unsatisfiedAlerts.stream().anyMatch(Alert::isSatisfied)) {
			Audio.playAlertAudio();
			eventBus.post(new AlertSatisfiedEvent());
		}
	}
	
	public boolean isSatisfied(Alert alert) {
		if(alert.isSatisfied()) return true;
		final Scrip scrip = scripService.findByIsin(alert.getIsin());
		final Quote quote = quoteService.get(scrip);
		System.out.println(quote + " : " + alert);
		return quote.getLtp() >= alert.getPrice();
	}
	
}
