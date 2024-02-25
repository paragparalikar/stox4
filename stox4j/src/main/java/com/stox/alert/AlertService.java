package com.stox.alert;

import java.util.Collection;

import com.stox.common.quote.Quote;
import com.stox.common.quote.QuoteService;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.util.Audio;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class AlertService {

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
					alert.setSatisfied(true);
					Audio.playAlertAudio();
				}
			});
		}
	}
	
}
