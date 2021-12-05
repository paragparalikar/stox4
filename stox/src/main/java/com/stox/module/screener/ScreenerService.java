package com.stox.module.screener;

import com.stox.module.core.model.Scrip;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;

@Getter
public class ScreenerService extends Service<ObservableList<Scrip>> {
	
	private final ScreenerConfig screenerConfig = new ScreenerConfig();
	private final ObservableList<Scrip> scrips = new SimpleListProperty<Scrip>();

	@Override
	protected Task<ObservableList<Scrip>> createTask() {
		return new Task<ObservableList<Scrip>>() {
			@Override
			protected ObservableList<Scrip> call() throws Exception {
				
				return scrips;
			}
		};
	}

}
