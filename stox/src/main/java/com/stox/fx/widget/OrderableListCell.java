package com.stox.fx.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class OrderableListCell<T> extends ListCell<T> {

	public OrderableListCell() {
		setOnDragDetected(this::onDragDetected);
		setOnDragEntered(this::onDragEntered);
		setOnDragOver(this::onDragOver);
		setOnDragExited(this::onDragExited);
		setOnDragDropped(this::onDragDropped);
	}
	
	private void onDragDetected(final MouseEvent event) {
		Optional.ofNullable(getItem()).ifPresent(item -> {
			final Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			dragboard.setDragView(snapshot(new SnapshotParameters(), null), event.getX(), event.getY());
			final ClipboardContent content = new ClipboardContent();
			final int currentIndex = getListView().getItems().indexOf(getItem());
			content.put(DataFormat.PLAIN_TEXT, String.valueOf(currentIndex));
			dragboard.setContent(content);
		});
		event.consume();
	}     
	
	private boolean isEligibleForDnd(final DragEvent event) {
		final String text = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
		final int sourceIndex = Integer.parseInt(text);
		final int currentIndex = getListView().getItems().indexOf(getItem());
		return sourceIndex != currentIndex && sourceIndex + 1 != currentIndex;
	}
	
	private void onDragEntered(final DragEvent event) {
		if (isEligibleForDnd(event)) {
			getStyleClass().add("dnd");
		}
		event.consume();
	}
	
	private void onDragOver(final DragEvent event) {
		if (isEligibleForDnd(event)) {
			event.acceptTransferModes(TransferMode.MOVE);
		}
		event.consume();
	}
	
	private void onDragExited(final DragEvent event) {
		if (isEligibleForDnd(event)) {
			getStyleClass().remove("dnd");
		}
	}
	
	private void onDragDropped(final DragEvent event) {
		if (isEligibleForDnd(event)) {
			final String text = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
			final int sourceIndex = Integer.parseInt(text);
			final List<T> items = new ArrayList<>(getListView().getItems());
			final int currentIndex = items.indexOf(getItem());
			items.add(currentIndex, items.remove(sourceIndex));
			getListView().getItems().setAll(items);
			getListView().getSelectionModel().select(currentIndex);
			event.setDropCompleted(true);
		}
		event.consume();
	}
	
}
