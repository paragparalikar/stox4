package com.stox.workbench.module;

import java.util.Objects;
import java.util.Optional;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentStackPane;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.handler.MovableMouseEventHandler;
import com.stox.fx.widget.handler.ResizeMouseEventHandler;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lombok.NonNull;

public abstract class ModuleView<T extends ModuleViewState> implements HasNode<FluentBorderPane> {

	private final FluentBorderPane container = new FluentBorderPane();
	private final FluentStackPane root = new FluentStackPane(container);
	private final FluentBorderPane resizableWrapper = ResizeMouseEventHandler.resizable(new FluentBorderPane());

	public abstract ModuleTitleBar getTitleBar();

	public abstract T stop(@NonNull final Bounds bounds);

	public ModuleView() {
		resizableWrapper.classes("module-view").center(root).addHandler(MouseEvent.MOUSE_PRESSED, e -> resizableWrapper.toFront());
	}

	public ModuleView<T> title(@NonNull final ModuleTitleBar titleBar) {
		container.top(titleBar.getNode());
		MovableMouseEventHandler.movable(titleBar.getNode(), resizableWrapper);
		return this;
	}

	public ModuleView<T> content(@NonNull final Node node) {
		container.center(node);
		return this;
	}
	
	public ModuleView<T> tool(final Node node){
		container.bottom(node);
		return this;
	}
	
	protected ModuleView<T> defaultBounds(@NonNull final FluentBorderPane container, @NonNull final Bounds bounds){
		container.width(bounds.getWidth() / 5).height(bounds.getHeight()).autosize();
		return this;
	}

	public ModuleView<T> start(final T state, @NonNull final Bounds bounds) {
		if (Objects.isNull(state)) {
			defaultBounds(resizableWrapper, bounds);
		} else {
			resizableWrapper.bounds(state.x() * bounds.getWidth(),
					state.y() * bounds.getHeight(),
					state.width() * bounds.getWidth(),
					state.height() * bounds.getHeight());
		}
		return this;
	}

	public T stop(@NonNull final T state, final Bounds bounds) {
		Optional.ofNullable(bounds).ifPresent(b -> state
				.x(resizableWrapper.x() / bounds.getWidth())
				.y(resizableWrapper.y() / bounds.getHeight())
				.width(resizableWrapper.width() / bounds.getWidth())
				.height(resizableWrapper.height() / bounds.getHeight()));
		return state;
	}

	@Override
	public FluentBorderPane getNode() {
		return resizableWrapper;
	}

}
