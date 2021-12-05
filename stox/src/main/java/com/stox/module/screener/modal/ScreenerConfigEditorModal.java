package com.stox.module.screener.modal;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentTab;
import com.stox.fx.fluent.scene.control.FluentTabPane;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.module.screener.ScreenerService;
import com.stox.workbench.modal.ActionModal;

import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScreenerConfigEditorModal extends ActionModal<ScreenerConfigEditorModal> {

	@NonNull private final FxMessageSource messageSource;
	@NonNull private final ScreenerService screenerService;
	@NonNull private final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers;
	
	private final FluentTabPane tabPane = new FluentTabPane();
	private final FluentBorderPane container = new FluentBorderPane(tabPane).classes("padded", "content").fullArea();
	
	@Override
	public ScreenerConfigEditorModal show(Node caller) {
		build();
		return super.show(caller);
	}
	
	private void build() {
		if(tabPane.getTabs().isEmpty()) {
			graphic(Icon.ALIGN_RIGHT)
				.title(messageSource.get("Modify Screener Configuration"))
				.content(container)
				.actionButtonText(messageSource.get("Run"))
				.cancelButtonText(messageSource.get("Cancel"));
			final List<ScripsSupplierView> scripsSupplierViews = screenerService
					.getScreenerConfig().getScripsSupplierViews();
			scripsSupplierViewSuppliers.stream()
				.map(Supplier::get)
				.map(view -> {scripsSupplierViews.add(view); return view;})
				.map(view -> new FluentTab(view.name(), view.getNode()))
				.forEach(tabPane.getTabs()::add);
		}
	}
	
	@Override
	protected void action() {
		super.hide();
		screenerService.restart();
	}

	@Override
	protected ScreenerConfigEditorModal getThis() {
		return this;
	}

}
