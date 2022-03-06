package com.stox.watchlist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public class WatchlistRepository {

	private final DynamoDbAsyncClient client;
	private final DynamoDbAsyncTable<Watchlist> table;
	
	public WatchlistRepository(DynamoDbAsyncClient client) {
		this.client = client;
		final DynamoDbEnhancedAsyncClient enhancedClient = DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(client).build();
		table = enhancedClient.table("watchlist", TableSchema.fromBean(Watchlist.class));
	}
	
	public CompletableFuture<List<Watchlist>> findAll() {
		final List<Watchlist> watchlists = new LinkedList<>();
		final CompletableFuture<List<Watchlist>> future = new CompletableFuture<List<Watchlist>>();
		table.scan().items()
			.doAfterOnError(future::completeExceptionally)
			.doAfterOnComplete(() -> future.complete(watchlists))
			.subscribe(watchlists::add);
		return future;
	}
	
	public CompletableFuture<Void> create(Watchlist watchlist) {
		return table.putItem(watchlist);
	}
	
	public CompletableFuture<Watchlist> delete(String name) {
		return table.deleteItem(Key.builder().partitionValue(name).build());
	}
	
	public CompletableFuture<Void> addEntry(String name, String isin){
		final UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
				.tableName("watchlist")
				.key(Collections.singletonMap("name", AttributeValue.builder().s(name).build()))
				.updateExpression("SET #entries = list_append(#entries, :isins)")
				.expressionAttributeNames(Collections.singletonMap("#entries", "entries"))
				.expressionAttributeValues(Collections.singletonMap(":isins", AttributeValue.builder().l(
						AttributeValue.builder().s(isin).build()).build()))
				.returnValues(ReturnValue.NONE)
				.build();
		return client.updateItem(updateItemRequest).thenApply(r -> null);
	}
	
	public CompletableFuture<Void> removeEntry(String name, int index){
		final UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
				.tableName("watchlist")
				.key(Collections.singletonMap("name", AttributeValue.builder().s(name).build()))
				.updateExpression("REMOVE entries[" + index + "]")
				.returnValues(ReturnValue.NONE)
				.build();
		return client.updateItem(updateItemRequest).thenApply(r -> null);
	}

}
