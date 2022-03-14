package com.stox.example;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.ta4j.core.Bar;

import com.stox.common.event.SelectedBarQueryEvent;
import com.stox.common.scrip.Scrip;
import com.stox.common.ui.Fx;
import com.stox.example.event.ExampleGroupCreatedEvent;
import com.stox.example.event.ExampleGroupDeletedEvent;
import com.stox.example.event.ExampleGroupUpdatedEvent;

import javafx.collections.FXCollections;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddAsExampleMenu extends Menu {

	private final EventBus eventBus;
	private final ExampleService exampleService;
	private final ExampleGroupService exampleGroupService;
	
	public void init() {
		setText("Add to example");
		eventBus.register(this);
		exampleGroupService.findAll().stream()
			.sorted(Comparator.comparing(ExampleGroup::getName))
			.forEach(this::addItem);
	}
	
	private Optional<MenuItem> findItem(String id){
		for(MenuItem item : getItems()) {
			final ExampleGroup group = ExampleGroup.class.cast(item.getUserData());
			if(Objects.equals(group.getId(), id)) {
				return Optional.of(item);
			}
		}
		return Optional.empty();
	}
	
	@Subscribe
	public void onExampleGroupCreated(ExampleGroupCreatedEvent event) {
		if(!findItem(event.getExampleGroup().getId()).isPresent()) {
			Fx.run(() -> {
				addItem(event.getExampleGroup());
				FXCollections.sort(getItems(), Comparator.comparing(MenuItem::getText));
			});
		}
	}
	
	@Subscribe
	public void onExampleGroupUpdated(ExampleGroupUpdatedEvent event) {
		findItem(event.getExampleGroup().getId()).ifPresent(item -> {
			Fx.run(() -> {
				getItems().remove(item);
				addItem(event.getExampleGroup());
				FXCollections.sort(getItems(), Comparator.comparing(MenuItem::getText));
			});
		});
	}
	
	@Subscribe
	public void onExampleGroupDeleted(ExampleGroupDeletedEvent event) {
		findItem(event.getExampleGroup().getId()).ifPresent(item -> {
			Fx.run(() -> getItems().remove(item));
		});
	}
	
	private void addItem(ExampleGroup group) {
		final MenuItem item = new MenuItem(group.getName());
		item.setUserData(group);
		getItems().add(item);
		item.setOnAction(event -> addTo(group));
	}
	
	private void addTo(ExampleGroup group) {
		final double screenX = getParentPopup().getX();
		final double screenY = getParentPopup().getY();
		final SelectedBarQueryEvent selectedBarQueryEvent = new SelectedBarQueryEvent(screenX, screenY);
		eventBus.post(selectedBarQueryEvent);
		final Bar bar = selectedBarQueryEvent.getBar();
		final Scrip scrip = selectedBarQueryEvent.getScrip();
		
		final Example example = new Example(scrip.getIsin(), group.getId(), bar.getEndTime());
		exampleService.create(example);
	}
	
}
