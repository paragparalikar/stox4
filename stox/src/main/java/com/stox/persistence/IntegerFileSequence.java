package com.stox.persistence;

import java.nio.file.Path;
import java.util.function.Supplier;

import com.stox.persistence.store.TextFileStore;
import com.stox.util.Strings;

import lombok.NonNull;

public class IntegerFileSequence implements Supplier<Integer> {

	private final TextFileStore textFileStore;

	public IntegerFileSequence(@NonNull final Path path) {
		this.textFileStore = new TextFileStore(path);
	}

	@Override
	public Integer get() {
		final String text = textFileStore.read();
		final Integer id = Strings.hasText(text) ? Integer.parseInt(text) + 1 : 1;
		textFileStore.write(id.toString());
		return id;
	}

}
