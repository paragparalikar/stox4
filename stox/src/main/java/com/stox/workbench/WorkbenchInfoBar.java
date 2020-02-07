package com.stox.workbench;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.FluentPane;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.HasNode;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkbenchInfoBar implements HasNode<Node>{

	@NonNull
	private final FxMessageSource messageSource;
	private final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	private final FluentLabel messageLabel = new FluentLabel().fullWidth();
	private final FluentPane messageLabelContainer = new FluentPane().fullWidth();
	private final FluentLabel lastDownloadDateLabel = new FluentLabel();
	private final FluentHBox container = new FluentHBox(messageLabelContainer, new Separator(Orientation.VERTICAL), lastDownloadDateLabel).classes("primary-background","info-bar");
	
	public WorkbenchInfoBar message(@NonNull final ObservableValue<String> message, final String style) {
		messageLabel.textProperty().bind(message);
		messageLabel.styleClass().clear();
		messageLabel.styleClass().addAll(style, "primary-background");
		messageLabelContainer.child(messageLabel);
		return this;
	}
	
	public WorkbenchInfoBar lastDownloadDate(@NonNull final Date date) {
		final ObservableValue<String> textValue = messageSource.get("Last Download Date");
		lastDownloadDateLabel.textProperty().unbind();
		lastDownloadDateLabel.textProperty().bind(new StringBinding() {
			{bind(textValue);}
			@Override
			protected String computeValue() {
				return textValue.getValue() + " : " + dateFormat.format(date);
			}
			
		});
		return this;
	}
	
	public WorkbenchInfoBar clear() {
		messageLabelContainer.children().clear();
		return this;
	}
	
	@Override
	public Node getNode() {
		return container;
	}

}
