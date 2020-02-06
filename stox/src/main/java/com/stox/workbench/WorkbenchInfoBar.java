package com.stox.workbench;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.control.FluentProgressBar;
import com.stox.fx.fluent.scene.layout.FluentPane;
import com.stox.fx.fluent.scene.layout.IFluentHBox;
import com.stox.fx.widget.Icon;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import lombok.NonNull;

public class WorkbenchInfoBar extends HBox implements IFluentHBox<WorkbenchInfoBar>{

	private final FluentLabel messageLabel = new FluentLabel().fullWidth();
	private final FluentPane messageLabelContainer = new FluentPane().fullWidth();
	private final FluentProgressBar progressBar = new FluentProgressBar(0).classes("primary");
	private final FluentPane progressBarContainer = new FluentPane();
	private final FluentLabel networkStatusLabel = new FluentLabel(Icon.CLOUD).classes("primary", "icon");
	private final FluentLabel diskStatusLabel = new FluentLabel(Icon.DATABASE).classes("primary", "icon");
	
	public WorkbenchInfoBar() {
		classes("primary-background","info-bar")
		.children(messageLabelContainer, new Separator(Orientation.VERTICAL),
				progressBarContainer, new Separator(Orientation.VERTICAL),
				networkStatusLabel, new Separator(Orientation.VERTICAL), 
				diskStatusLabel);
	}
	
	public WorkbenchInfoBar message(@NonNull final ObservableValue<String> message, final String messageStyle, final double progress, final String progressStyle) {
		message(message, messageStyle);
		progressBarContainer.child(progressBar.styleClass(progressStyle).progress(progress));
		return this;
	}
	
	public WorkbenchInfoBar message(@NonNull final ObservableValue<String> message, final String style) {
		messageLabel.textProperty().bind(message);
		messageLabel.styleClass().clear();
		messageLabel.styleClass().addAll(style, "primary-background");
		messageLabelContainer.child(messageLabel);
		progressBarContainer.children().clear();
		return this;
	}
	
	public WorkbenchInfoBar clear() {
		messageLabelContainer.children().clear();
		progressBarContainer.children().clear();
		return this;
	}
	
	
	
	
	
	@Override
	public WorkbenchInfoBar getThis() {
		return this;
	}

}
