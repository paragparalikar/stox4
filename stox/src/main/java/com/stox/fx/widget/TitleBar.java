package com.stox.fx.widget;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.FluentVBox;

import javafx.geometry.Side;
import javafx.scene.Node;
import lombok.NonNull;

public class TitleBar implements HasNode<Node>{

	private final FluentHBox right = new FluentHBox(), left = new FluentHBox();
	private final FluentVBox top = new FluentVBox().classes("spaced"), bottom = new FluentVBox().classes("spaced");
	private final FluentBorderPane container = new FluentBorderPane(null, top, right, bottom, left).classes("primary-background", "title-bar");

	public TitleBar center(final Node node) {
		container.center(node);
		return this;
	}
	
	public TitleBar append(@NonNull final Side side, @NonNull final Node node) {
		switch (side) {
			case BOTTOM:
				container.bottom(bottom.child(0, node));
				break;
			case LEFT:
				container.left(left.child(0, node));
				break;
			case RIGHT:
				container.right(right.child(0, node));
				break;
			case TOP:
				container.top(top.child(0, node));
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
					container.bottom(null);
				}
				break;
			case LEFT:
				left.children().remove(node);
				if (left.children().isEmpty()) {
					container.left(null);
				}
				break;
			case RIGHT:
				right.children().remove(node);
				if (right.children().isEmpty()) {
					container.right(null);
				}
				break;
			case TOP:
				top.children().remove(node);
				if (top.children().isEmpty()) {
					container.top(null);
				}
				break;
			default:
				break;
		}
		return this;
	}

	@Override
	public Node getNode() {
		return container;
	}

}
