package com.stox.fx.workbench;

import com.stox.Context;
import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.fluent.scene.layout.FluentStackPane;
import com.stox.fx.fluent.scene.layout.IFluentBorderPane;
import com.stox.fx.widget.DockableArea;
import com.stox.fx.widget.Icon;
import com.stox.fx.workbench.event.ModuleViewCloseRequestEvent;

import javafx.geometry.Side;
import javafx.scene.layout.BorderPane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public abstract class ModuleView<T extends ModuleView<T>> extends BorderPane implements IFluentBorderPane<T>, DockableArea<T>{

	@Getter
	private final Context context;
	@Getter(AccessLevel.PACKAGE)
	private final TitleBar titleBar;
	private final FluentBorderPane container = new FluentBorderPane();
	private final FluentStackPane root = new FluentStackPane(container);
	private final FluentButton closeButton = new FluentButton(Icon.TIMES)
			.onAction(event -> fireEvent(new ModuleViewCloseRequestEvent(this)))
			.classes("primary","icon","hover-danger");
	
	public ModuleView(@NonNull final Context context) {
		this.context = context;
		container.top(titleBar = buildTitleBar().append(Side.RIGHT, closeButton));
		classes("module-view").dockable(container.top()).center(root);
	}
	
	public T initDefaultBounds(final double width, final double height) {
		layoutX(width/4).layoutY(height/4).width(width/2).height(height/2).autosize();
		return getThis();
	}
	
	protected abstract TitleBar buildTitleBar();
	
}
