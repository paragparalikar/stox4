package com.stox.module.ranker;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.stox.fx.widget.Ui;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.persistence.ScripRepository;
import com.stox.module.ranker.model.Ranker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RankerService extends Service<ObservableList<Scrip>> {
	
	private final BarRepository barRepository;
	private final ScripRepository scripRepository;
	private final RankerConfig rankerConfig = new RankerConfig();
	private final ObservableList<Scrip> scrips = FXCollections.observableArrayList();

	@Override
	protected Task<ObservableList<Scrip>> createTask() {
		return new Task<ObservableList<Scrip>>() {
			private final Map<Scrip, Double> ranks = new HashMap<>();
			
			@Override
			protected ObservableList<Scrip> call() throws Exception {
				scrips.clear();
				final Ranker ranker = rankerConfig.getRanker();
				final int minBarCount = ranker.minBarCount(rankerConfig.getRankerConfig());
				final List<Scrip> eligibleScrips = scripRepository.find(Exchange.NSE);
				
				for(int index = 0; index < eligibleScrips.size(); index++) {
					final long progress = index;
					final Scrip scrip = eligibleScrips.get(index);
					try {
						final List<Bar> bars = barRepository.find(scrip.isin(), BarSpan.D, minBarCount);
						if(minBarCount <= bars.size()) {
							final double rank = ranker.rank(eligibleScrips, ranker);
							ranks.put(scrip, rank);
							System.out.println("ranked " + scrip.name());
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
					Ui.fx(() -> updateProgress(progress, eligibleScrips.size()));
				}
				final List<Scrip> scrips_ = ranks.entrySet().stream()
					.sorted(Comparator.comparing(Entry::getValue))
					.map(Entry::getKey)
					.collect(Collectors.toList());
				scrips.setAll(scrips_);
				return scrips;
			}
		};
	}
	
	
}
