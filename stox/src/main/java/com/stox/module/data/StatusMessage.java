package com.stox.module.data;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class StatusMessage{
	
	private ObservableValue<String> message;
	private SimpleObjectProperty<Boolean> success = new SimpleObjectProperty<Boolean>(null);
	
	public StatusMessage(@NonNull final ObservableValue<String> message) {
		this.message = message;
	}

	public void success(final boolean success) {
		this.success.set(success);
	}
		
}