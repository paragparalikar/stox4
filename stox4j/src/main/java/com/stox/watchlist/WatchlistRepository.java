package com.stox.watchlist;

import java.util.function.Consumer;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

public class WatchlistRepository {

	private final DynamoDbAsyncTable<Watchlist> table;
	
	public WatchlistRepository(DynamoDbEnhancedAsyncClient client) {
		table = client.table("watchlist", TableSchema.fromBean(Watchlist.class));
	}
	
	public void findAll(Consumer<Watchlist> consumer, Runnable onComplete) {
		final PagePublisher<Watchlist> publisher = table.scan();
		publisher.subscribe(page -> page.items().forEach(consumer));
		publisher.doAfterOnError(error -> error.printStackTrace());
		publisher.doAfterOnComplete(onComplete);
	}
	
	public void create(Watchlist watchlist) {
		table.putItem(watchlist);
	}
	
	public void update(Watchlist watchlist) {
		table.updateItem(watchlist);
	}
	
	public void delete(Watchlist watchlist) {
		table.deleteItem(watchlist);
	}
}
