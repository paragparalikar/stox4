package com.stox.module.ranker.modal;

import java.util.Collection;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentTab;
import com.stox.fx.fluent.scene.control.FluentTabPane;
import com.stox.fx.fluent.scene.layout.FluentBorderPane;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.workbench.modal.ActionModal;

import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RankerConfigEditorModal extends ActionModal<RankerConfigEditorModal> {

	@NonNull private final FxMessageSource messageSource;
	@NonNull private final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers;
	private final FluentTabPane tabPane = new FluentTabPane();
	private final FluentBorderPane container = new FluentBorderPane(tabPane).classes("padded", "content").fullArea();
	
	@Override
	public RankerConfigEditorModal show(Node caller) {
		build();
		return super.show(caller);
	}
	
	private void build() {
		if(tabPane.getTabs().isEmpty()) {
			graphic(Icon.ALIGN_RIGHT)
				.title(messageSource.get("Modify Ranker Configuration"))
				.content(container)
				.actionButtonText(messageSource.get("Modify"))
				.cancelButtonText(messageSource.get("Cancel"));
			scripsSupplierViewSuppliers.stream()
				.map(Supplier::get)
				.map(view -> new FluentTab(view.name(), view.getNode()))
				.forEach(tabPane.getTabs()::add);
		}
	}
	
	@Override
	protected void action() {
	}

	@Override
	protected RankerConfigEditorModal getThis() {
		return this;
	}

}
