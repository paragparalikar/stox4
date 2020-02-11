package com.stox.module.data;

import java.util.Optional;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusMessageListCell extends ListCell<StatusMessage> {

	private FluentLabel graphic;
	private StatusMessage previousItem;
	private final ProgressIndicator progressIndicator;
	private final ChangeListener<String> messageListener = (o,old,value) ->  Ui.fx(() -> setText(value));
	private final ChangeListener<Boolean> successListener = (o,old,value) -> Ui.fx(() -> statusChanged(value));

	@Override
	protected void updateItem(StatusMessage item, boolean empty) {
		super.updateItem(item, empty);
		Optional.ofNullable(previousItem).ifPresent(previousItem -> {
			previousItem.message().removeListener(messageListener);
			previousItem.success().removeListener(successListener);
		});
		if (null == item || empty) {
			setGraphic(null);
			setText(null);
		} else {
			item.message().addListener(messageListener);
			setText(item.message().getValue());
			item.success().addListener(successListener);
			statusChanged(item.success().getValue());
		}
		previousItem = item;
	}
	
	private void statusChanged(final Boolean success) {
		if (null == success) {
			setGraphic(progressIndicator);
		} else {
			if (null == graphic) {
				graphic = new FluentLabel().classes("icon", "inverted", "transparent-background");
			}
			setGraphic(graphic);
			graphic.text(success ? Icon.CHECK : Icon.EXCLAMATION_TRIANGLE)
					.classes(success, "success")
					.classes(!success, "danger");
		}
	}

}
