package com.stox.module.ranker;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Icon;
import com.stox.module.core.widget.supplier.scrip.ScripsSupplierView;
import com.stox.module.ranker.modal.RankerConfigEditorModal;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.event.ActionEvent;
import javafx.geometry.Side;
import lombok.NonNull;

public class RankerTitleBar extends ModuleTitleBar {

	private final FxMessageSource messageSource;
	private RankerConfigEditorModal rankerConfigEditorModal;
	private final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers;
	private final FluentButton configButton = new FluentButton(Icon.GEAR)
				.classes("primary", "icon")
				.addHandler(ActionEvent.ACTION, event -> configEditor());

	public RankerTitleBar(
			@NonNull final FxMessageSource messageSource,
			@NonNull final Collection<Supplier<ScripsSupplierView>> scripsSupplierViewSuppliers) {
		this.messageSource = messageSource;
		this.scripsSupplierViewSuppliers = scripsSupplierViewSuppliers;
		getTitleBar().append(Side.RIGHT, configButton);
	}

	private void configEditor() {
		Optional.ofNullable(rankerConfigEditorModal)
			.orElseGet(() -> rankerConfigEditorModal = new RankerConfigEditorModal(messageSource, scripsSupplierViewSuppliers))
			.show(getNode());
	}
	
}
