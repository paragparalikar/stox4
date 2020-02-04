package com.stox.fx.workbench;

import java.util.Optional;

import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.IFluentBorderPane;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import lombok.NonNull;

public class TitleBar extends BorderPane implements IFluentBorderPane<TitleBar>{

	private FluentHBox top, right, bottom, left;
	
	public TitleBar(final ObservableValue<String> titleValue) {
		final Label titleLabel = new Label();
		titleLabel.textProperty().bind(titleValue);
		center(titleLabel).classes("primary-background","title-bar");
		getTop();
	}
	
	public TitleBar append(@NonNull final Side side, @NonNull final Node node) {
		switch(side) {
			case BOTTOM:
				Optional.ofNullable(bottom).orElseGet(() -> bottom = new FluentHBox()).child(node);
				break;
			case LEFT:
				Optional.ofNullable(left).orElseGet(() -> left = new FluentHBox()).child(node);
				break;
			case RIGHT:
				Optional.ofNullable(right).orElseGet(() -> right = new FluentHBox()).child(node);
				break;
			case TOP:
				Optional.ofNullable(top).orElseGet(() -> top = new FluentHBox()).child(node);
				break;
			default:
				break;
		}
		return this;
	}
	
	public TitleBar remove(@NonNull final Side side, @NonNull final Node node) {
		switch(side) {
			case BOTTOM:
				Optional.ofNullable(bottom).ifPresent(parent -> parent.children().remove(node));
				break;
			case LEFT:
				Optional.ofNullable(left).ifPresent(parent -> parent.children().remove(node));
				break;
			case RIGHT:
				Optional.ofNullable(right).ifPresent(parent -> parent.children().remove(node));
				break;
			case TOP:
				Optional.ofNullable(top).ifPresent(parent -> parent.children().remove(node));
				break;
			default:
				break;
		}
		return this;
	}
		
	@Override
	public TitleBar getThis() {
		return this;
	}

}
