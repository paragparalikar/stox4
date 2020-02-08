package com.stox.workbench.module;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

import com.stox.Context;
import com.stox.fx.widget.Ui;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UiModule<T extends ModuleViewState> implements Module {

	@NonNull
	@Getter(AccessLevel.PROTECTED)
	private final Context context;

	private final Set<ModuleView<T>> views = Collections.newSetFromMap(new WeakHashMap<>());

	protected abstract String getIcon();

	protected abstract String getCode();

	protected abstract ObservableValue<String> getModuleName();

	protected abstract ModuleView<T> buildModuleView();

	@Override
	public void start() {
		addMenuItem();
		load();
	}

	private void load() {
		final Set<T> states = context.getModuleStateRepository().read(getCode());
		Optional.ofNullable(states).ifPresent(this::load);
	}
	
	private void load(@NonNull final Set<T> states) {
		states.forEach(this::load);
	}
	
	private void load(@NonNull final T state) {
		context.getScheduledExecutorService().submit(() -> {
			final ModuleView<T> view = cache(buildModuleView());
			final Bounds bounds = context.getWorkbench().visualBounds();
			final double width = bounds.getWidth();
			final double height = bounds.getHeight();
			Ui.fx(()->{
				context.getWorkbench().add(view);
				view.getNode().bounds(state.getX()*width, state.getY()*height, state.getWidth()*width, state.getHeight()*height);
				view.state(state);
			});
		});
	}

	private void addMenuItem() {
		context.getWorkbench().getTitleBar().getMenuBar().newMenuItem(getIcon(), getModuleName(), event -> {
			final ModuleView<T> view = cache(buildModuleView());
			final Bounds bounds = context.getWorkbench().add(view).visualBounds();
			view.initDefaultBounds(bounds.getWidth(), bounds.getHeight());
		});
	}
	
	private T updateState(ModuleView<T> view){
		final T state = view.state();
		final Bounds bounds = view.getNode().getBoundsInParent();
		final Bounds parentBounds = context.getWorkbench().visualBounds();
		final double width = parentBounds.getWidth();
		final double height = parentBounds.getHeight();
		state.setX(bounds.getMinX()/width);
		state.setY(bounds.getMinY()/height);
		state.setWidth(bounds.getWidth()/width);
		state.setHeight(bounds.getHeight()/height);
		return state;
	}

	private ModuleView<T> cache(@NonNull final ModuleView<T> view) {
		views.add(view);
		view.getNode().parentProperty().addListener((o,old,value) -> {
			if(Objects.isNull(value)) {
				views.remove(view);
			}
		});
		return view;
	}

	@Override
	public void stop() {
		context.getModuleStateRepository().write(getCode(), views.stream().map(this::updateState).collect(Collectors.toSet()));
	}

}
