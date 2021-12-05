package com.stox.module.screener;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.fx.widget.search.SearchBox;
import com.stox.fx.widget.search.SearchableListView;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.CoreConstant;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.module.screener.modal.ScreenerConfigEditorModal;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.control.Toggle;
import lombok.NonNull;

public class ScreenerTitleBar extends ModuleTitleBar {

	private final FxMessageSource messageSource;
	private final ScreenerService screenerService;
	private ScreenerConfigEditorModal screenerConfigEditorModal;
	private final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers;
	private final FluentButton configButton = new FluentButton(Icon.GEAR)
				.classes("primary", "icon")
				.addHandler(ActionEvent.ACTION, event -> configEditor());
	
	private final Toggle searchToggle;
	private final SearchBox<Scrip> searchBox;
	private final LinkButton linkButton = new LinkButton();
	
	public ScreenerTitleBar(
			@NonNull final FxMessageSource messageSource,
			@NonNull final SearchableListView<Scrip> listView,
			@NonNull final ScreenerService screenerService,
			@NonNull final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers) {
		this.messageSource = messageSource;
		this.screenerService = screenerService;
		this.scripsSupplierViewSuppliers = scripsSupplierViewSuppliers;
		this.searchBox = new SearchBox<Scrip>(listView, this::test);
		getTitleBar().append(Side.RIGHT, linkButton);
		getTitleBar().append(Side.RIGHT, configButton);
		searchToggle = appendToggleNode(Icon.SEARCH, searchBox.getNode());
		searchToggle.selectedProperty().addListener(this::searchToggleSelected);
		listView.getSelectionModel().selectedItemProperty().addListener(this::scripSelectionChanged);
	}
	
	private void searchToggleSelected(final ObservableValue<? extends Boolean> observable, final Boolean old, final Boolean value) {
		if(value) searchBox.clear().focus();
	}
	
	private void scripSelectionChanged(final ObservableValue<? extends Scrip> observable, final Scrip old, final Scrip scrip) {
		if(null != scrip) {
			linkButton.getLink().setState(LinkState.builder()
					.put(CoreConstant.KEY_TO, String.valueOf(0))
					.put(CoreConstant.KEY_ISIN, scrip.isin())
					.build());
		}
	}

	private void configEditor() {
		Optional.ofNullable(screenerConfigEditorModal)
			.orElseGet(() -> screenerConfigEditorModal = new ScreenerConfigEditorModal(messageSource, screenerService, scripsSupplierViewSuppliers))
			.show(getNode());
	}
	
	public boolean test(final Scrip scrip, String text) {
		text = text.trim().toLowerCase();
		return null != scrip && (scrip.name().trim().toLowerCase().contains(text) ||
				scrip.code().trim().toLowerCase().contains(text) ||
				scrip.isin().trim().toLowerCase().contains(text));
	}
	
}
