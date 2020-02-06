package com.stox.workbench.event;

import com.stox.workbench.module.ModuleView;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class ModuleViewCloseRequestEvent extends Event {
	private static final long serialVersionUID = -7614759355915311427L;

	public static final EventType<ModuleViewCloseRequestEvent> TYPE = new EventType<>("ModuleViewCloseRequestEvent");

	@Getter
	private final ModuleView<?> moduleView;
	
	public ModuleViewCloseRequestEvent(final ModuleView<?> moduleView) {
		super(TYPE);
		this.moduleView = moduleView;
	}

}
