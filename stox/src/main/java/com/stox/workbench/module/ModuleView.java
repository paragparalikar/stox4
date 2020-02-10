package com.stox.workbench.module;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentStackPane;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.handler.MovableMouseEventHandler;
import com.stox.fx.widget.handler.ResizeMouseEventHandler;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;

public abstract class ModuleView implements HasNode<FluentBorderPane> {

	private final FluentBorderPane container = new FluentBorderPane();
	private final FluentStackPane root = new FluentStackPane(container);
	private final FluentBorderPane resizableWrapper = ResizeMouseEventHandler.resizable(new FluentBorderPane());

	public abstract ModuleTitleBar getTitleBar();

	public ModuleView() {
		resizableWrapper.classes("module-view").center(root).addHandler(MouseEvent.MOUSE_PRESSED, e -> resizableWrapper.toFront());
	}

	protected ModuleView title(@NonNull final ModuleTitleBar titleBar) {
		container.top(titleBar.getNode());
		MovableMouseEventHandler.movable(titleBar.getNode(), resizableWrapper);
		return this;
	}

	protected ModuleView content(@NonNull final Node node) {
		container.center(node);
		return this;
	}

	public ModuleView initDefaultBounds(@NonNull final Bounds bounds) {
		resizableWrapper.width(bounds.getWidth() / 5).height(bounds.getHeight()).autosize();
		return this;
	}

	public ModuleView state(@NonNull final ModuleViewState state, @NonNull final Bounds bounds) {
		resizableWrapper.bounds(state.x() * bounds.getWidth(), 
				state.y() * bounds.getHeight(), 
				state.width() * bounds.getWidth(), 
				state.height() * bounds.getHeight());
		return this;
	}

	public ModuleViewState state(@NonNull final Bounds bounds) {
		return new ModuleViewState()
				.x(resizableWrapper.x() / bounds.getWidth())
				.y(resizableWrapper.y() / bounds.getHeight())
				.width(resizableWrapper.width() / bounds.getWidth())
				.height(resizableWrapper.height() / bounds.getHeight());
	}

	@Override
	public FluentBorderPane getNode() {
		return resizableWrapper;
	}

}
