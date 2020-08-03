package com.stox.workbench.link;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;

import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Link {
	
	GREEN(Color.GREEN), BLUE(Color.BLUE), RED(Color.RED), YELLOW(Color.YELLOW);
	
	public static Link link(@NonNull final String colorString) {
		final Color color = Color.web(colorString);
		return Arrays.stream(Link.values()).filter(link -> Objects.equals(color, link.getColor())).findFirst().orElse(null);
	}
	
	private LinkState state;
	private final Color color;
	private final Set<Consumer<LinkState>> listeners = Collections.newSetFromMap(new WeakHashMap<>());
	
	public LinkState getState() {
		return state;
	}
	
	public void setState(@NonNull LinkState linkState) {
		if(!Objects.equals(state, linkState)) {
			this.state = linkState;
			listeners.forEach(listener -> listener.accept(linkState));
		}
	}
	
	public Color getColor() {
		return color;
	}
	
	public void add(@NonNull Consumer<LinkState> listener){
		listeners.add(listener);
		listener.accept(state);
	}
	
	public void remove(@NonNull Consumer<LinkState> listener){
		listeners.remove(listener);
	}
	
}
