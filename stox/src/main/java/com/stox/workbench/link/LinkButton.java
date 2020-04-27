package com.stox.workbench.link;

import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

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
	private final Set<Consumer<LinkState>> linkStateConsumers = Collections.newSetFromMap(new IdentityHashMap<>());
	
	public LinkButton() {
		getStyleClass().add("link-button");
		setBackground(Background.EMPTY);
		Arrays.stream(Link.values()).forEach(link -> add(link));
		linkProperty.addListener((o, old, link) -> linkChanged(old, link));
		setLink(Link.GREEN);
	}

	private void add(final Link link) {
		final MenuItem menuItem = new MenuItem();
		menuItem.setGraphic(createGraphic(link.getColor()));
		menuItem.addEventHandler(ActionEvent.ACTION, event -> setLink(link));
		getItems().add(menuItem);
	}

	private Node createGraphic(final Color color) {
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
	
	public LinkButton add(final Consumer<LinkState> linkStateConsumer) {
		linkStateConsumers.add(linkStateConsumer);
		Optional.ofNullable(linkProperty.get()).ifPresent(link -> link.add(linkStateConsumer));
		return this;
	}
	
	private void linkChanged(final Link old, final Link link) {
		Optional.ofNullable(old).ifPresent(oldLink -> linkStateConsumers.forEach(consumer -> oldLink.remove(consumer)));
		Optional.ofNullable(link).ifPresent(newLink -> {
			setGraphic(createGraphic(link.getColor()));
			linkStateConsumers.forEach(consumer -> newLink.add(consumer));
		});
	}
}
