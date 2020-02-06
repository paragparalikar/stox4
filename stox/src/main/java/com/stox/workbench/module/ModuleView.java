package com.stox.workbench.module;

import com.stox.Context;
import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.FluentStackPane;
import com.stox.fx.fluent.scene.layout.IFluentBorderPane;
import com.stox.fx.widget.DockableArea;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Spacer;
import com.stox.fx.widget.TitleBar;
import com.stox.workbench.event.ModuleViewCloseRequestEvent;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.layout.BorderPane;
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

	public T initDefaultBounds(final double width, final double height) {
		layoutX(width / 4).layoutY(height / 4).width(width / 2).height(height / 2).autosize();
		return getThis();
	}

	protected TitleBar buildTitleBar(@NonNull final String icon, @NonNull final ObservableValue<String> titleValue) {
		final FluentLabel titleLabel = new FluentLabel().classes("primary");
		titleLabel.textProperty().bind(titleValue);
		final FluentButton closeButton = new FluentButton(Icon.TIMES)
				.onAction(event -> fireEvent(new ModuleViewCloseRequestEvent(this)))
				.classes("primary", "icon", "hover-danger");
		return new TitleBar().append(Side.RIGHT, closeButton).center(new FluentHBox(new FluentLabel(icon).classes("primary", "icon"), titleLabel, new Spacer()));
	}

}
