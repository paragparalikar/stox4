package com.stox.watchlist;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class Watchlist implements Comparable<Watchlist> {

	private String name;
	@DynamoDbPartitionKey
	public String getName() { return name; 	}
	public void setName(String name) { this.name = name; }
	
	private List<String> entries;
	public List<String> getEntries() { return entries; }
	public void setEntries(List<String> entries) { this.entries = entries; }
	
	@Override
	public int compareTo(Watchlist o) {
		return Objects.compare(name, o.getName(), Comparator.naturalOrder());
	}
	
}
