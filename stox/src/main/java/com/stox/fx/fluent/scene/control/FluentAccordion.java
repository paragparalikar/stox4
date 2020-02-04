package com.stox.fx.fluent.scene.control;

import java.util.Collection;

import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class FluentAccordion extends Accordion implements IFluentControl<FluentAccordion> {

	public FluentAccordion() {
		super();
	}

	public FluentAccordion(TitledPane... titledPanes) {
		super(titledPanes);
	}

	public FluentAccordion(Collection<? extends TitledPane> titledPanes) {
		super(titledPanes.toArray(new TitledPane[0]));
	}

	@Override
	public FluentAccordion getThis() {
		return this;
	}

	public FluentAccordion expandedPane(TitledPane value) {
		setExpandedPane(value);
		return this;
	}

	public TitledPane expandedPane() {
		return getExpandedPane();
	}

	public ObservableList<TitledPane> panes() {
		return getPanes();
	}

	public FluentAccordion panes(TitledPane... panes) {
		panes().addAll(panes);
		return this;
	}

	public FluentAccordion panes(Collection<? extends TitledPane> panes) {
		panes().addAll(panes);
		return this;
	}

}
