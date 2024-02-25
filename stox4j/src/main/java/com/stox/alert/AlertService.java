package com.stox.alert;

import java.nio.file.Path;
import java.util.Collection;

import com.stox.common.quote.Quote;
import com.stox.common.quote.QuoteService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class AlertService {

	private final Path home;
	private final ScripService scripService;
	private final QuoteService quoteService;
	@Delegate private final AlertRepository alertRepository;

	public void poll() {
		final Collection<Alert> alerts = alertRepository.findAll();
		for(Alert alert : alerts) {
			Thread.ofVirtual().start(() -> {
				final Scrip scrip = scripService.findByIsin(alert.getIsin());
				final Quote quote = quoteService.get(scrip);
				if(quote.getLtp() >= alert.getPrice()) {
					executeAlert(alert);
				}
			});
		}
	}
	
	@SneakyThrows
	private void executeAlert(Alert alert) {
		System.out.println(alert);
		alert.setSatisfied(true);
	}
	
}
