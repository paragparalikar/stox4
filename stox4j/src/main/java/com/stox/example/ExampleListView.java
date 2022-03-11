package com.stox.example;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.Icon;
import com.stox.example.event.ExampleAddedEvent;
import com.stox.example.event.ExampleGroupClearedEvent;
import com.stox.example.event.ExampleGroupSelectedEvent;
import com.stox.example.event.ExampleRemovedEvent;
import com.stox.example.event.ExampleSelectedEvent;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ExampleListView extends ListView<Example> {

	private ExampleGroup group;
	private final EventBus eventBus;
	private final ScripService scripService;
	private final ExampleService exampleService;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public ExampleListView(EventBus eventBus, ScripService scripService, ExampleService exampleService) {
		this.eventBus = eventBus;
		this.scripService = scripService;
		this.exampleService = exampleService;
		setCellFactory(this::createExampleCell);
		getSelectionModel().selectedItemProperty().addListener(this::changed);
		eventBus.register(this);
	}
	
	@Subscribe
	public void onExampleGroupSelected(ExampleGroupSelectedEvent event) {
		if(null == group || !Objects.equals(group, event.getExampleGroup())) {
			this.group = event.getExampleGroup();
			Optional.ofNullable(group)
				.map(ExampleGroup::getId)
				.map(exampleService::findByGroupId)
				.ifPresent(getItems()::setAll);
		}
	}
	
	@Subscribe
	public void onExampleGroupCleared(ExampleGroupClearedEvent event) {
		if(null != group && Objects.equals(group.getId(), event.getId())) {
			getItems().clear();
		}
	}
	
	@Subscribe
	public void onExampleAdded(ExampleAddedEvent event) {
		if(null != group && Objects.equals(group.getId(), event.getExample().getGroupId())) {
			getItems().add(event.getExample());
		}
	}
	
	@Subscribe
	public void onExampleRemoved(ExampleRemovedEvent event) {
		if(null != group && Objects.equals(group.getId(), event.getExample().getGroupId())) {
			getItems().remove(event.getExample());
		}
	}
	
	private void changed(ObservableValue<? extends Example> observable, Example oldValue, Example newValue) {
		Optional.ofNullable(newValue)
			.map(ExampleSelectedEvent::new)
			.ifPresent(eventBus::post);
	}
	
	private ListCell<Example> createExampleCell(ListView<Example> listView){
		return new ListCell<Example>() {
			private final Button deleteButton = new Button(Icon.TRASH);
			{deleteButton.getStyleClass().setAll("icon", "button");}
			@Override
			protected void updateItem(Example item, boolean empty) {
				super.updateItem(item, empty);
				if(empty || null == item) {
					setText(null);
					setGraphic(null);
				} else {
					final Scrip scrip = scripService.findByIsin(item.getIsin());
					final String scripName = null == scrip ? item.getIsin() : scrip.getName();
					setText(scripName + " (" + formatter.format(item.getTimestamp()) + ")");
					setGraphic(deleteButton);
					deleteButton.setOnAction(event -> exampleService.delete(item));
				}
			}
		};
	}
}
