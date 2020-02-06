package com.stox.workbench.module;

import com.stox.Context;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentStackPane;
import com.stox.fx.fluent.scene.layout.IFluentBorderPane;
import com.stox.fx.widget.DockableArea;
import com.stox.fx.widget.TitleBar;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.NonNull;

public abstract class ModuleView<T extends ModuleView<T>> extends BorderPane implements IFluentBorderPane<T>, DockableArea<T> {

	@Getter
	private final Context context;
	@Getter
	private final TitleBar titleBar;
	private final FluentBorderPane container = new FluentBorderPane();
	private final FluentStackPane root = new FluentStackPane(container);

	public ModuleView(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue, @NonNull final Context context) {
		this.context = context;
		container.top(titleBar = buildTitleBar(icon, titleValue));
		classes("module-view").dockable(titleBar).center(root);
	}
	
	protected T content(final Node node) {
		container.center(node);
		return getThis();
	}

	public T initDefaultBounds(final double width, final double height) {
		width(width / 5).height(height).autosize();
		return getThis();
	}

	protected TitleBar buildTitleBar(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue) {
		return new ModuleTitleBar(icon, titleValue, this::onClose);
	}

	protected T onClose(final ActionEvent event) {
		final Pane pane = Pane.class.cast(getParent());
		pane.getChildren().remove(this);
		return getThis();
	}
}
