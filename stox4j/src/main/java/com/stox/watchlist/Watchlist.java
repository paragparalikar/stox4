package com.stox.watchlist;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@DynamoDBTable(tableName = "watchlist")
public class Watchlist {

	@DynamoDBHashKey
	private String name;
	
	@DynamoDBTyped(DynamoDBAttributeType.L)
	private List<String> entries = new ArrayList<>();
	
	public Watchlist(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
