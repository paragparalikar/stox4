package com.stox.workbench.link;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;

import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;

import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;

@RequiredArgsConstructor
public enum Link {
	
	GREEN(Color.GREEN), BLUE(Color.BLUE), RED(Color.RED), YELLOW(Color.YELLOW);
	
	private State state;
	private final Color color;
	private final Set<Consumer<State>> listeners = Collections.newSetFromMap(new WeakHashMap<>());
	
	public State getState() {
		return state;
	}
	
	public void setState(@NonNull State state) {
		this.state = state;
		listeners.forEach(listener -> listener.accept(state));
	}
	
	public Color getColor() {
		return color;
	}
	
	public void add(@NonNull Consumer<State> listener){
		listeners.add(listener);
		listener.accept(state);
	}
	
	public void remove(@NonNull Consumer<State> listener){
		listeners.remove(listener);
	}
	
	@Value
	@Wither
	@Accessors(fluent = true)
	public static class State{
		
		private long to;
		
		private Scrip scrip;
		
		private BarSpan barSpan;
		
		
	}
	
}
