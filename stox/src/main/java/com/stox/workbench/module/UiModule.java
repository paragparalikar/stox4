package com.stox.workbench.module;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

import com.stox.Context;

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
		menuItem();
		load();
	}

	private void load() {
		final Type genericSuperClass = getClass().getGenericSuperclass();
		final Type stateType = ParameterizedType.class.cast(genericSuperClass).getActualTypeArguments()[0];
		final Set<T> states = context.getModuleStateRepository().read(getCode(), stateType); 
		Optional.ofNullable(states).ifPresent(this::load);
	}
	
	private void load(@NonNull final Set<T> states) {
		states.forEach(this::load);
	}
	
	private void load(@NonNull final T state) {
		add(moduleView(buildModuleView())).start(state, context.getWorkbench().visualBounds());
	}

	private void menuItem() {
		context.getWorkbench().getTitleBar().getMenuBar().newMenuItem(getIcon(), getModuleName(), event -> {
			add(moduleView(buildModuleView())).start(null, context.getWorkbench().visualBounds());
		});
	}
	
	private ModuleView<T> moduleView(@NonNull final ModuleView<T> view) {
		view.getTitleBar().icon(getIcon()).title(getModuleName()).closeEventHandler(event -> remove(view));
		return view;
	}
	
	private ModuleView<T> add(@NonNull final ModuleView<T> moduleView) {
		views.add(context.getWorkbench().add(moduleView));
		return moduleView;
	}
	
	protected ModuleView<T> remove(@NonNull final ModuleView<T> moduleView) {
		views.remove(context.getWorkbench().remove(moduleView));
		return moduleView;
	}

	@Override
	public void stop() {
		final Bounds bounds = context.getWorkbench().visualBounds();
		final Set<ModuleViewState> states = views.stream().map(view -> view.stop(bounds)).collect(Collectors.toSet());
		context.getModuleStateRepository().write(getCode(), states);
	}

}
