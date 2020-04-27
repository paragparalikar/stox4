package com.stox.module.charting.widget;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.axis.horizontal.XAxis;
import com.stox.module.charting.event.PanRequestEvent;
import com.stox.module.charting.event.ZoomRequestEvent;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class NavigationBar extends FluentHBox {

	private final XAxis xAxis;
	private final Button leftButton = new FluentButton(Icon.ARROW_LEFT).classes("icon");
	private final Button zoomOutButton = new FluentButton(Icon.MINUS).classes("icon", "bold");
	private final Button zoomInButton = new FluentButton(Icon.PLUS).classes("icon", "bold");
	private final Button rightButton = new FluentButton(Icon.ARROW_RIGHT).classes("icon", "bold");

	public NavigationBar(final XAxis xAxis) {
		this.xAxis = xAxis;
		leftButton.addEventHandler(ActionEvent.ACTION, e -> left());
		zoomOutButton.addEventHandler(ActionEvent.ACTION, e -> zoomOut());
		zoomInButton.addEventHandler(ActionEvent.ACTION, e -> zoomIn());
		rightButton.addEventHandler(ActionEvent.ACTION, e -> right());
		managed(false).children(leftButton, zoomOutButton, zoomInButton, rightButton).classes("charting-navigation-bar");
		Ui.box(this);
	}

	private void left() {
		fireEvent(new PanRequestEvent(xAxis.getUnitWidth()));
	}

	private void zoomIn() {
		fireEvent(new ZoomRequestEvent(xAxis.getWidth() / 2, 5));
	}

	private void zoomOut() {
		fireEvent(new ZoomRequestEvent(xAxis.getWidth() / 2, -5));
	}

	private void right() {
		xAxis.pan(-1 * xAxis.getUnitWidth());
		fireEvent(new PanRequestEvent(-1 * xAxis.getUnitWidth()));
	}

}
