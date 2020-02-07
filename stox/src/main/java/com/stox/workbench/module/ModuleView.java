package com.stox.workbench.module;

import com.stox.Context;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentStackPane;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.handler.MovableMouseEventHandler;
import com.stox.fx.widget.handler.ResizeMouseEventHandler;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public abstract class ModuleView implements HasNode<FluentBorderPane>{

	@Getter(AccessLevel.PROTECTED)
	private final Context context;
	@Getter
	private final ModuleTitleBar titleBar;
	private final FluentBorderPane container = new FluentBorderPane();
	private final FluentStackPane root = new FluentStackPane(container);
	private final FluentBorderPane resizableWrapper = ResizeMouseEventHandler.resizable(new FluentBorderPane());

	public ModuleView(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final Context context) {
		this.context = context;
		titleBar = buildTitleBar(icon, titleValue);
		container.top(titleBar.getNode());
		MovableMouseEventHandler.movable(titleBar.getNode(), resizableWrapper);
		resizableWrapper.classes("module-view").center(root).addHandler(MouseEvent.MOUSE_PRESSED, e -> resizableWrapper.toFront());
	}
	
	protected ModuleView content(final Node node) {
		container.center(node);
		return this;
	}

	public ModuleView initDefaultBounds(final double width, final double height) {
		resizableWrapper.width(width / 5).height(height).autosize();
		return this;
	}

	protected ModuleTitleBar buildTitleBar(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue) {
		return new ModuleTitleBar(icon, titleValue, this::onClose);
	}

	protected void onClose(final ActionEvent event) {
		final Pane pane = Pane.class.cast(resizableWrapper.getParent());
		pane.getChildren().remove(resizableWrapper);
	}
	
	@Override
	public FluentBorderPane getNode() {
		return resizableWrapper;
	}
}
