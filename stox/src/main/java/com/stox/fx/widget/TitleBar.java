package com.stox.fx.widget;

import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.IFluentBorderPane;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import lombok.NonNull;

public class TitleBar extends BorderPane implements IFluentBorderPane<TitleBar> {

	private final FluentHBox 
			top = new FluentHBox(), 
			right = new FluentHBox(), 
			bottom = new FluentHBox(), 
			left = new FluentHBox();

	public TitleBar() {
		classes("primary-background", "title-bar");
	}

	public TitleBar append(@NonNull final Side side, @NonNull final Node node) {
		switch (side) {
			case BOTTOM:
				bottom(bottom.child(node));
				break;
			case LEFT:
				left(left.child(node));
				break;
			case RIGHT:
				right(right.child(node));
				break;
			case TOP:
				top(top.child(node));
				break;
			default:
				break;
		}
		return this;
	}

	public TitleBar remove(@NonNull final Side side, @NonNull final Node node) {
		switch (side) {
			case BOTTOM:
				bottom.children().remove(node);
				if (bottom.children().isEmpty()) {
					bottom(null);
				}
				break;
			case LEFT:
				left.children().remove(node);
				if (left.children().isEmpty()) {
					left(null);
				}
				break;
			case RIGHT:
				right.children().remove(node);
				if (right.children().isEmpty()) {
					right(null);
				}
				break;
			case TOP:
				top.children().remove(node);
				if (top.children().isEmpty()) {
					top(null);
				}
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
