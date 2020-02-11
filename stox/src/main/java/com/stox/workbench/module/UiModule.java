package com.stox.workbench.module;

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
public abstract class UiModule implements Module {

	@NonNull
	@Getter(AccessLevel.PROTECTED)
	private final Context context;
	
	private final Set<ModuleView> views = Collections.newSetFromMap(new WeakHashMap<>());

	protected abstract String getIcon();

	protected abstract String getCode();

	protected abstract ObservableValue<String> getModuleName();

	protected abstract ModuleView buildModuleView();

	@Override
	public void start() {
		menuItem();
		load();
	}

	private void load() {
		Optional.ofNullable(context.getModuleStateRepository().read(getCode())).ifPresent(this::load);
	}
	
	private void load(@NonNull final Set<ModuleViewState> states) {
		states.forEach(this::load);
	}
	
	private void load(@NonNull final ModuleViewState state) {
		add(moduleView(buildModuleView())).state(state, context.getWorkbench().visualBounds());
	}

	private void menuItem() {
		context.getWorkbench().getTitleBar().getMenuBar().newMenuItem(getIcon(), getModuleName(), event -> {
			add(moduleView(buildModuleView())).initDefaultBounds(context.getWorkbench().visualBounds());
		});
	}
	
	private ModuleView moduleView(@NonNull final ModuleView view) {
		view.getTitleBar().icon(getIcon()).title(getModuleName()).closeEventHandler(event -> remove(view));
		return view;
	}
	
	private ModuleView add(@NonNull final ModuleView moduleView) {
		views.add(context.getWorkbench().add(moduleView));
		return moduleView;
	}
	
	protected ModuleView remove(@NonNull final ModuleView moduleView) {
		views.remove(context.getWorkbench().remove(moduleView));
		return moduleView;
	}

	@Override
	public void stop() {
		final Bounds bounds = context.getWorkbench().visualBounds();
		final Set<ModuleViewState> states = views.stream().map(view -> view.state(bounds)).collect(Collectors.toSet());
		context.getModuleStateRepository().write(getCode(), states);
	}

}
