package com.stox.watchlist;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class Watchlist {

	private final StringProperty nameProperty = new SimpleStringProperty();
	private final ListProperty<String> entriesProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public Watchlist(String name) { nameProperty.set(name); }
	
	public String getName() { return nameProperty.get(); 	}
	public void setName(@NonNull String name) { nameProperty.set(name.trim()); }
	public StringProperty nameProperty() {return nameProperty;}
	
	public List<String> getEntries() { return entriesProperty; }
	public void setEntries(List<String> entries) { entriesProperty.setAll(entries); }
	public ListProperty<String> entriesProperty() {return entriesProperty;}
	
}
