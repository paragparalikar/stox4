package com.stox.workbench.modal;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.handler.MovableMouseEventHandler;
import com.stox.fx.widget.handler.ResizeMouseEventHandler;
import com.stox.workbench.modal.event.ModalHideRequestEvent;
import com.stox.workbench.modal.event.ModalShowRequestEvent;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import lombok.NonNull;

public abstract class Modal<T extends Modal<T>> implements HasNode<Node> {

	private final ModalTitleBar titleBar = new ModalTitleBar();
	private final FluentBorderPane container = new FluentBorderPane().top(titleBar.getNode());
	private final FluentBorderPane node = ResizeMouseEventHandler.resizable(new FluentBorderPane()).center(container).managed(false).classes("modal");

	protected abstract T getThis();
	
	public Modal() {
		MovableMouseEventHandler.movable(titleBar.getNode(), node);
		titleBar.closeEventHandler(e -> hide());
	}
	
	public T show(@NonNull final Node caller) {
		caller.fireEvent(new ModalShowRequestEvent(this));
		return getThis();
	}

	public T hide() {
		node.fireEvent(new ModalHideRequestEvent(this));
		return getThis();
	}
	
	public T graphic(final String node) {
		titleBar.graphic(node);
		return getThis();
	}
	
	public T title(final ObservableValue<String> titleValue) {
		titleBar.title(titleValue);
		return getThis();
	}
	
	public T content(final Node node) {
		container.center(node);
		return getThis();
	}
	
	public T tool(final Node node) {
		container.bottom(node);
		return getThis();
	}
	
	public T danger() {
		node.classes("danger");
		return getThis();
	}
	
	@Override
	public Node getNode() {
		return node;
	}
	
	public double initialHeight() {
		return 300;
	}
	
	public double initialWidth() {
		return 500;
	}
}
