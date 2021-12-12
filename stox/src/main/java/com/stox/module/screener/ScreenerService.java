package com.stox.module.screener;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.stox.fx.widget.Ui;
import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.BarRepository;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.module.screen.Screen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScreenerService extends Service<ObservableList<Scrip>> {
	
	private final BarRepository barRepository;
	private final ScreenerConfig screenerConfig = new ScreenerConfig();
	private final ObservableList<Scrip> scrips = FXCollections.observableArrayList();

	@Override
	protected Task<ObservableList<Scrip>> createTask() {
		return new Task<ObservableList<Scrip>>() {
			@Override
			protected ObservableList<Scrip> call() throws Exception {
				scrips.clear();
				final List<Scrip> eligibleScrips = screenerConfig.getScripsSupplierViews().stream()
					.map(ScripsSupplierView::get)
					.flatMap(Collection::stream)
					.distinct()
					.sorted()
					.collect(Collectors.toList());
				
				for(int index = 0; index < eligibleScrips.size(); index++) {
					final long progress = index;
					final Scrip scrip = eligibleScrips.get(index);
					if(test(scrip)) {
						Ui.fx(() -> scrips.add(scrip));
					}
					Ui.fx(() -> updateProgress(progress, eligibleScrips.size()));
				}
				return scrips;
			}
		};
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean test(Scrip scrip) {
		try {
			final int span = screenerConfig.getSpan();
			final int offset = screenerConfig.getOffset();
			final Screen screen = screenerConfig.getScreen();
			final Object screenConfig = screenerConfig.getScreenConfig();
			final int totalBarCount = screen.minBarCount(screenConfig) + span + offset;
			
			final List<Bar> bars = barRepository.find(scrip.isin(), BarSpan.D, totalBarCount);
			
			if(totalBarCount > bars.size()) {
				return false;
			}
			
			final List<Bar> offsetBars = bars.subList(offset, bars.size());
			
			for(int index = 0; index < span; index++) {
				final List<Bar> spanBars = offsetBars.subList(index, offsetBars.size());
				if(screen.match(spanBars, screenConfig)) {
					return true;
				}
			}
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
