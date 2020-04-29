package com.stox.persistence.store;

import java.nio.file.Path;
import java.text.DateFormat;
import java.util.Date;

import com.stox.util.Strings;

import lombok.NonNull;
import lombok.SneakyThrows;

public class DateFileStore implements Store<Date> {

	private final DateFormat dateFormat;
	private final TextFileStore textFileStore;
	
	public DateFileStore(@NonNull final Path path) {
		this(path, DateFormat.getDateInstance());
	}
	
	public DateFileStore(@NonNull final Path path, @NonNull final DateFormat dateFormat) {
		this.dateFormat = dateFormat;
		this.textFileStore = new TextFileStore(path);
	}

	@Override
	@SneakyThrows
	public Date read() {
		final String text = textFileStore.read();
		return Strings.hasText(text) ? dateFormat.parse(text) : null;
	}

	@Override
	public Date write(@NonNull final Date data) {
		textFileStore.write(dateFormat.format(data));
		return data;
	}

}
