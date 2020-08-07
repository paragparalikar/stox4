package com.stox.module.core.widget.supplier.scrip;

import java.util.Collection;
import java.util.stream.Collectors;

import com.stox.fx.fluent.scene.control.FluentListView;
import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.persistence.ScripRepository;

import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExchangeScripSupplierView implements ScripsSupplierView {

	@NonNull private final ScripRepository scripRespository;
	private FluentListView<Exchange> exchangeListView = new FluentListView<Exchange>()
			.items(Exchange.values()).multipleSelectionMode().select(Exchange.NSE);
	
	@Override
	public String name() {
		return "Exchanges";
	}

	@Override
	public Node getNode() {
		return exchangeListView;
	}

	@Override
	public Collection<Scrip> get() {
		return exchangeListView.getSelectionModel().getSelectedItems().stream()
				.map(scripRespository::find)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

}
