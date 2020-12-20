package com.stox.module.data.cleanup.split;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.util.EventBus;
import com.stox.util.MathUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SplitCorrectionAction {
	private static final DateFormat FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	
	public static void main(String[] args) throws InterruptedException {
		final EventBus eventBus = new EventBus();
		final Path home = Paths.get(System.getProperty("user.home"), ".stox4");
		final BarRepository barRepository = new BarRepository(home);
		final ScripRepository scripRepository = new ScripRepository(home, eventBus);
		scripRepository.init();
		final List<Scrip> scrips = scripRepository.find(Exchange.NSE);
		for(final Scrip scrip : scrips) {
			process(scrip, barRepository);
		}
	}
	
	private static void process(final Scrip scrip, final BarRepository barRepository) {
		try {
			final List<Bar> bars = barRepository.bars(scrip.isin(), BarSpan.D, Integer.MAX_VALUE);
			final boolean found = process(scrip, bars);
			if(found) barRepository.save(scrip.isin(), BarSpan.D, bars);
		} catch(Exception e) {
			System.out.println(scrip.name() + " - " + e.getMessage());
		}
	}
	
	private static boolean process(final Scrip scrip, final List<Bar> bars) {
		boolean found = false;
		for(int index = 0; index < bars.size() - 1; index++) {
			final Bar currentBar = bars.get(index);
			final Bar previousBar = bars.get(index + 1);
			final double ratio = previousBar.close() / currentBar.open();
			if(ratio > 1.9) {
				found = true;
				final double factor =  MathUtil.round(ratio);
				for(int j = index + 1; j < bars.size(); j++) {
					final Bar bar = bars.get(j);
					bar.apply(factor);
				}
				System.out.printf("Split : %s %f %s\n", FORMAT.format(new Date(currentBar.date())), factor, scrip.name());
			}
		}
		return found;
	}
	
}
