package com.stox.module.watchlist.widget;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Supplier;

import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.listener.CompositeChangeListener;
import com.stox.fx.widget.listener.RootBinderSceneChangeListener;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.watchlist.event.WatchlistCreatedEvent;
import com.stox.module.watchlist.event.WatchlistDeletedEvent;
import com.stox.module.watchlist.event.WatchlistEntryCreatedEvent;
import com.stox.module.watchlist.event.WatchlistUpdatedEvent;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.module.watchlist.repository.WatchlistRepository;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lombok.Builder;
import lombok.NonNull;

public class AddToWatchlistMenu extends Menu {

	private final Node root;
	private final Supplier<Scrip> scripSupplier;
	private final Supplier<BarSpan> barSpanSupplier;
	private final ChangeListener<Scene> sceneChangeListener = new CompositeChangeListener<>(
			new RootBinderSceneChangeListener<>(WatchlistCreatedEvent.TYPE, this::created),
			new RootBinderSceneChangeListener<>(WatchlistUpdatedEvent.TYPE, this::updated),
			new RootBinderSceneChangeListener<>(WatchlistDeletedEvent.TYPE, this::deleted));

	@Builder
	public AddToWatchlistMenu(
			@NonNull final Node root, 
			@NonNull final FxMessageSource messageSource,
			@NonNull final Supplier<Scrip> scripSupplier,
			@NonNull final Supplier<BarSpan> barSpanSupplier,
			@NonNull final WatchlistRepository watchlistRepository) {
		this.root = root;
		this.scripSupplier = scripSupplier;
		this.barSpanSupplier = barSpanSupplier;
		textProperty().bind(messageSource.get("Add to watchlist"));
		root.sceneProperty().addListener(new WeakChangeListener<>(sceneChangeListener));
		sceneChangeListener.changed(root.sceneProperty(), root.getScene(), root.getScene());
		watchlistRepository.findAll().forEach(watchlist -> getItems().add(menuItem(watchlist)));
		sort();
	}
	
	private void sort() {
		final Comparator<MenuItem> comparator = (one, two) -> Objects.compare(one.getText(), two.getText(), Comparator.naturalOrder());
		FXCollections.sort(getItems(), comparator);
	}
	
	private void addTo(final Watchlist watchlist) {
		final Scrip scrip = scripSupplier.get();
		final BarSpan barSpan = barSpanSupplier.get();
		if(null != scrip && null != barSpan && !watchlist.contains(scrip.isin(), barSpan)) {
			final WatchlistEntry entry = WatchlistEntry.builder()
					.scrip(scrip)
					.barSpan(barSpan)
					.watchlist(watchlist)
					.build();
			watchlist.put(entry);
			root.fireEvent(new WatchlistEntryCreatedEvent(watchlist, entry));
		}
	}
	
	private MenuItem menuItem(final Watchlist watchlist) {
		final MenuItem item = new MenuItem(watchlist.name());
		item.setUserData(watchlist);
		item.setOnAction(event -> addTo(watchlist));
		return item;
	}
	
	private void created(final WatchlistCreatedEvent event) {
		getItems().add(menuItem(event.watchlist()));
		sort();
	}

	private void updated(final WatchlistUpdatedEvent event) {
		getItems().stream()
			.filter(item -> Objects.equals(((Watchlist)item.getUserData()).name(), event.watchlist().name()))
			.findFirst()
			.ifPresent(item -> item.setText(event.watchlist().name()));
		sort();
	}

	private void deleted(final WatchlistDeletedEvent event) {
		final Iterator<MenuItem> iterator = getItems().iterator();
		while(iterator.hasNext()) {
			final MenuItem item = iterator.next();
			if(Objects.equals(((Watchlist)item.getUserData()).name(), event.watchlist().name())) {
				iterator.remove();
				break;
			}
		}
	}

}
