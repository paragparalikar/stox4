package com.stox.example;

import java.util.Comparator;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.ui.Fx;
import com.stox.example.event.ExampleGroupCreatedEvent;
import com.stox.example.event.ExampleGroupDeletedEvent;
import com.stox.example.event.ExampleGroupSelectedEvent;
import com.stox.example.event.ExampleGroupUpdatedEvent;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class ExampleGroupComboBox extends ComboBox<ExampleGroup> {

	private final EventBus eventBus;
	
	public ExampleGroupComboBox(EventBus eventBus) {
		this.eventBus = eventBus;
		eventBus.register(this);
		getSelectionModel().selectedItemProperty().addListener(this::changed);
	}
	
	private void changed(ObservableValue<? extends ExampleGroup> observable, ExampleGroup oldValue, ExampleGroup newValue) {
		eventBus.post(new ExampleGroupSelectedEvent(newValue));
	}
	
	private Optional<ExampleGroup> findItem(String id) {
		return getItems().stream().filter(item -> id.equals(item.getId())).findFirst();
	}
	
	@Subscribe
	public void onExampleGroupCreated(ExampleGroupCreatedEvent event) {
		Fx.run(() -> {
			getItems().add(event.getExampleGroup());
			FXCollections.sort(getItems(), Comparator.comparing(ExampleGroup::getName));
			getSelectionModel().select(event.getExampleGroup());
		});
	}
	
	@Subscribe
	public void onExampleGroupUpdated(ExampleGroupUpdatedEvent event) {
		findItem(event.getExampleGroup().getId()).ifPresent(group -> {
			Fx.run(() -> {
				group.setName(event.getExampleGroup().getName());
				FXCollections.sort(getItems(), Comparator.comparing(ExampleGroup::getName));
				getSelectionModel().select(group);
			});
		});
	}
	
	@Subscribe
	public void onExampleGroupDeleted(ExampleGroupDeletedEvent event) {
		findItem(event.getExampleGroup().getId()).ifPresent(group -> {
			Fx.run(() -> {
				getItems().remove(group);
				if(!getItems().isEmpty()) getSelectionModel().select(0);
			});
		});
	}
}

