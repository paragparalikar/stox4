package com.stox.workbench.link;

import java.util.Arrays;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.Ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class LinkButton extends MenuButton {

	private final ObjectProperty<Link> linkProperty = new SimpleObjectProperty<>();
	
	public LinkButton() {
		getStyleClass().add("link-button");
		setBackground(Background.EMPTY);
		Arrays.stream(Link.values()).forEach(link -> add(link));
		linkProperty.addListener((o, old, link) -> setGraphic(createGraphic(link.getColor())));
		setLink(Link.GREEN);
	}

	private void add(final Link link) {
		final MenuItem menuItem = new MenuItem();
		menuItem.setGraphic(createGraphic(link.getColor()));
		menuItem.addEventHandler(ActionEvent.ACTION, event -> setLink(link));
		getItems().add(menuItem);
	}

	Node createGraphic(final Color color) {
		final Label label = new FluentLabel(Icon.LINK).classes("icon");
		label.setStyle("link-color: "+Ui.web(color)+";");
		return label;
	}

	public void setLink(final Link link) {
		linkProperty.set(link);
		setGraphic(createGraphic(link.getColor()));
	}

	public Link getLink() {
		return linkProperty.get();
	}
	
	public ObjectProperty<Link> getLinkProperty() {
		return linkProperty;
	}

	
}
