package com.stox.watchlist;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import lombok.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Watchlist {

	private final StringProperty nameProperty = new SimpleStringProperty();
	private final ListProperty<String> entriesProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	@DynamoDbPartitionKey
	public String getName() { return nameProperty.get(); 	}
	public void setName(@NonNull String name) { nameProperty.set(name.trim()); }
	public StringProperty nameProperty() {return nameProperty;}
	
	public List<String> getEntries() { return entriesProperty; }
	public void setEntries(List<String> entries) { entriesProperty.setAll(entries); }
	public ListProperty<String> entriesProperty() {return entriesProperty;}
	
}
