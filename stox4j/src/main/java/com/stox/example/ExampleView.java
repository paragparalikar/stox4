package com.stox.example;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.greenrobot.eventbus.EventBus;

import com.stox.common.SerializationService;
import com.stox.common.scrip.ScripService;
import com.stox.common.ui.View;
import com.sun.javafx.scene.control.skin.ListViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import javafx.application.Platform;
import javafx.scene.control.IndexedCell;
import javafx.scene.layout.BorderPane;

public class ExampleView extends BorderPane implements View {
	
	private final ExampleListView exampleListView;
	private final ExampleGroupComboBox exampleGroupComboBox;
	private final ExampleGroupTitleBar exampleGroupTitleBar;
	private final ExampleGroupControlsMenuButton exampleGroupControlsMenuButton;
	
	private final ExampleGroupService exampleGroupService;
	private final SerializationService serializationService;
	
	public ExampleView(
			EventBus eventBus, 
			ScripService scripService, 
			ExampleService exampleService,
			ExampleGroupService exampleGroupService,
			SerializationService serializationService) {
		this.exampleGroupService = exampleGroupService;
		this.serializationService = serializationService;
		exampleListView = new ExampleListView(eventBus, scripService, exampleService);
		exampleGroupComboBox = new ExampleGroupComboBox(eventBus);
		exampleGroupControlsMenuButton = new ExampleGroupControlsMenuButton(exampleService,
				exampleGroupService, exampleGroupComboBox);
		exampleGroupTitleBar = new ExampleGroupTitleBar(exampleGroupComboBox, exampleGroupControlsMenuButton);
		setTop(exampleGroupTitleBar);
		setCenter(exampleListView);
	}
	
	@Override
	public void load() {
		final List<ExampleGroup> exampleGroups = exampleGroupService.findAll();
		exampleGroups.sort(Comparator.comparing(ExampleGroup::getName));
		final ExampleViewState state = serializationService.deserialize(ExampleViewState.class);
		Platform.runLater(() -> {
			exampleGroupComboBox.getItems().addAll(exampleGroups);
			if(null != state) {
				exampleGroups.stream().filter(Predicate.isEqual(state.getSelectedGroup()))
					.findAny().ifPresent(group -> exampleGroupComboBox.getSelectionModel().select(group));
				exampleListView.getItems().stream().filter(Predicate.isEqual(state.getSelectedItem()))
					.findAny().ifPresent(item -> exampleListView.getSelectionModel().select(item));
				exampleListView.getItems().stream().filter(Predicate.isEqual(state.getFirstVisibleItem()))
					.findFirst().ifPresent(exampleListView::scrollTo);
			}
		});
	}
	
	@Override
	public void unload() {
		final ExampleViewState state = new ExampleViewState();
		final ExampleGroup selectedGroup = exampleGroupComboBox.getValue();
		state.setSelectedGroup(selectedGroup);
		
		final Example selectedItem = exampleListView.getSelectionModel().getSelectedItem();
		state.setSelectedItem(selectedItem);
		
		final ListViewSkin<?> listViewSkin = (ListViewSkin<?>) exampleListView.getSkin();
	    final VirtualFlow<?> virtualFlow = (VirtualFlow<?>) listViewSkin.getChildren().get(0);
	    final Example firstVisibleExample = (Example) Optional.ofNullable(virtualFlow.getFirstVisibleCell())
	    		.map(IndexedCell::getItem).orElse(null);
	    state.setFirstVisibleItem(firstVisibleExample);
	    serializationService.serialize(state);
	}
}
