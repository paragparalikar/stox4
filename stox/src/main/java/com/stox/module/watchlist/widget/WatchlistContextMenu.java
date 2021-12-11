package com.stox.module.watchlist.widget;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.form.TextFormField;
import com.stox.module.core.model.BarSpan;
import com.stox.module.watchlist.model.Watchlist;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.util.Strings;
import com.stox.workbench.modal.ActionModal;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class WatchlistContextMenu extends ContextMenu {

	private final ListView<WatchlistEntry> entryListView;
	
	public WatchlistContextMenu(FxMessageSource messageSource, ListView<WatchlistEntry> entryListView) {
		super();
		this.entryListView = entryListView;
		final Consumer<Integer> callback = this::send;
		
		getItems().addAll(
				new SendToTopMenuItem(entryListView, messageSource, callback),
				new SendToBottomMenuItem(entryListView, messageSource, callback, entryListView.getItems()::size),
				new SendToIndexMenuItem(entryListView, messageSource, callback));
	}
	
	protected void send(int index) {
		if (0 <= index && index < entryListView.getItems().size()) {
			final BarSpan barSpan = entryListView.getItems().get(0).barSpan();
			final List<WatchlistEntry> items = Watchlist.class.cast(entryListView.getUserData()).entries().get(barSpan);
			final WatchlistEntry entry = entryListView.getSelectionModel().getSelectedItem().index(index);
			items.remove(entry);
			items.add(index, entry);
			for(int i = 0; i < items.size(); i++) {
				items.get(i).index(i);
			}
			items.sort(Comparator.naturalOrder());
			FXCollections.sort(entryListView.getItems());
		}
	}
	
}

class IndexInputModal extends ActionModal<IndexInputModal> {

	private final Consumer<Integer> callback;
	private final FxMessageSource messageSource;
	private final TextFormField indexFormField = new TextFormField().mandatory();
	private final FluentBorderPane container = new FluentBorderPane(indexFormField.getNode())
			.classes("padded", "content").fullArea();
	
	public IndexInputModal(FxMessageSource messageSource, final Consumer<Integer> callback) {
		this.callback = callback;
		this.messageSource = messageSource;
		indexFormField.name(messageSource.get("Index"));
		title(messageSource.get("Send To Index"))
			.graphic(Icon.ANCHOR)
			.content(container)
			.actionButtonText(messageSource.get("Send"))
			.cancelButtonText(messageSource.get("Cancel"));
	}
	
	@Override
	protected void action() {
		final String value = indexFormField.value();
		if(Strings.hasText(value)) {
			try {
				callback.accept(Integer.parseInt(value));
				hide();
			} catch(NumberFormatException e) {
				indexFormField.error(messageSource.get("Not a valid number"));
			}
		}
	}

	@Override
	protected IndexInputModal getThis() {
		return this;
	}
	
}

class SendToIndexMenuItem extends MenuItem implements EventHandler<ActionEvent> {

	private final Node caller;
	private final Consumer<Integer> callback;
	private final FxMessageSource messageSource;
	
	public SendToIndexMenuItem(final Node caller, final FxMessageSource messageSource, Consumer<Integer> callback) {
		this.caller = caller;
		this.callback = callback;
		this.messageSource = messageSource;
		addEventHandler(ActionEvent.ACTION, this);
		textProperty().bind(messageSource.get("Send To Index"));
	}
	
	@Override
	public void handle(ActionEvent event) {
		new IndexInputModal(messageSource, callback).show(caller);
	}
	
}

class SendToTopMenuItem extends SendToIndexMenuItem {
	
	private final Consumer<Integer> callback;
	
	public SendToTopMenuItem(final Node caller, final FxMessageSource messageSource, final Consumer<Integer> callback) {
		super(caller, messageSource, callback);
		this.callback = callback;
		textProperty().unbind();
		textProperty().bind(messageSource.get("Send To Top"));
	}
	
	@Override
	public void handle(ActionEvent event) {
		callback.accept(0);
	}
}

class SendToBottomMenuItem extends SendToIndexMenuItem {
	
	private final Consumer<Integer> callback;
	private final Supplier<Integer> sizeSupplier;
	
	public SendToBottomMenuItem(final Node caller, final FxMessageSource messageSource, final Consumer<Integer> callback, Supplier<Integer> sizeSupplier) {
		super(caller, messageSource, callback);
		this.sizeSupplier = sizeSupplier;
		this.callback = callback;
		textProperty().unbind();
		textProperty().bind(messageSource.get("Send To Bottom"));
	}
	
	@Override
	public void handle(ActionEvent event) {
		final int size = sizeSupplier.get();
		if(0 < size) {
			callback.accept(size - 1);
		}
	}
}