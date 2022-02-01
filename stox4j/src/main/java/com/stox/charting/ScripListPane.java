package com.stox.charting;

import java.util.List;

import org.springframework.stereotype.Component;

import com.stox.common.util.fx.FunctionStringConverter;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

@Component
public class ScripListPane extends BorderPane implements ChangeListener<ScripListView> {

	private final ChoiceBox<ScripListView> listChoiceBox = new ChoiceBox<>();
	
	public ScripListPane(final List<ScripListView> scripListViews) {
		setBottom(new StackPane(listChoiceBox));
		listChoiceBox.getItems().addAll(scripListViews);
		listChoiceBox.getSelectionModel().selectedItemProperty().addListener(this);
		listChoiceBox.setConverter(new FunctionStringConverter<ScripListView>(ScripListView::getDisplayName));
		Platform.runLater(() -> listChoiceBox.getSelectionModel().select(0));
	}
	
	@Override
	public void changed(ObservableValue<? extends ScripListView> observable, ScripListView oldValue,
			ScripListView newValue) {
		setCenter(newValue.getNode());
	}
	
}
