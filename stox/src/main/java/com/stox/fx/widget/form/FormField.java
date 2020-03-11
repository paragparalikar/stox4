package com.stox.fx.widget.form;

import java.util.Optional;

import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.fluent.scene.layout.FluentVBox;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Spacer;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public abstract class FormField<V, T extends FormField<V, T>> implements HasNode<Node> {
	
	private final FluentLabel nameLabel = new FluentLabel();
	private final FluentLabel errorLabel = new FluentLabel().classes("danger", "inverted");
	private final FluentLabel requiredLabel = new FluentLabel("*").classes("danger", "inverted");
	private final FluentHBox hbox = new FluentHBox(nameLabel, new Spacer(), errorLabel).classes("spaced");
	private final FluentVBox container = new FluentVBox(hbox).classes("form-field", "spaced").fullArea();
	
	protected abstract T getThis();
	
	public abstract V value();
	
	public abstract T value(V value);
	
	public T name(final ObservableValue<String> nameValue) {
		return bind(nameLabel, nameValue);
	}
		
	public T error(final ObservableValue<String> errorValue){
		return bind(errorLabel, errorValue);
	}
	
	protected T widget(final Node node) {
		container.child(node);
		return getThis();
	}
	
	public T mandatory() {
		hbox.child(0, requiredLabel);
		return getThis();
	}
	
	public T optional() {
		hbox.children().remove(requiredLabel);
		return getThis();
	}
	
	private T bind(final FluentLabel label, final ObservableValue<String> value) {
		label.textProperty().unbind();
		label.text(null);
		Optional.ofNullable(value).ifPresent(label.textProperty()::bind);
		return getThis();
	}
	
	@Override
	public Node getNode() {
		return container;
	}
}
