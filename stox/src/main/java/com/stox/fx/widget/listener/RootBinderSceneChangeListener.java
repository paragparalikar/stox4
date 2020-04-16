package com.stox.fx.widget.listener;

import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RootBinderSceneChangeListener<T extends Event> implements ChangeListener<Scene>{
	
	private final EventType<T> eventType;
	private final EventHandler<? super T> eventHandler;
	
	@Override
	public void changed(ObservableValue<? extends Scene> observableSceneValue, Scene old, Scene scene) {
		Optional.ofNullable(old).map(Scene::getRoot).ifPresent(this::unbind);
		Optional.ofNullable(scene).map(Scene::getRoot).ifPresent(this::bind);
	}
	
	private void bind(final Node root) {
		root.addEventHandler(eventType, eventHandler);
	}
	
	private void unbind(final Node root) {
		root.removeEventHandler(eventType, eventHandler);
	}

}

