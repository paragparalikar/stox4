package com.stox.module.charting;

import java.util.List;
import java.util.function.Supplier;

public interface Attachable {

	public static <T extends Attachable> void ensureSize(final List<T> attachables, final int size,
			final Supplier<T> supplier) {
		while (size > attachables.size()) {
			final T attachable = supplier.get();
			attachables.add(attachable);
			attachable.attach();
		}
		while (size < attachables.size() && 0 < attachables.size()) {
			final List<T> removableUnits = attachables.subList(size, attachables.size());
			removableUnits.forEach(Attachable::detach);
			removableUnits.clear();
		}
	}

	void attach();

	void detach();

}
