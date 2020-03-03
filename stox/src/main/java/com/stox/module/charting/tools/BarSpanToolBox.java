package com.stox.module.charting.tools;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import com.stox.fx.fluent.scene.control.FluentRadioButton;
import com.stox.fx.widget.FxMessageSource;
import com.stox.fx.widget.Ui;
import com.stox.module.charting.ChartingView;
import com.stox.module.core.model.BarSpan;
import com.stox.workbench.link.Link;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.link.LinkState;

import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lombok.NonNull;

public class BarSpanToolBox extends HBox implements ChartingToolBox {

	private final ChartingView chartingView;
	private final FxMessageSource messageSource;
	private final ToggleGroup toggleGroup = new ToggleGroup();
	private final Consumer<LinkState> linkStateConsumer = this::linkStateChanged;

	public BarSpanToolBox(@NonNull final FxMessageSource messageSource, @NonNull final ChartingView chartingView) {
		this.chartingView = chartingView;
		this.messageSource = messageSource;
		Arrays.asList(BarSpan.values()).forEach(this::buildToggle);

		final LinkButton linkButton = chartingView.getTitleBar().getLinkButton();
		linkButton.getLinkProperty().addListener((o, old, link) -> linkChanged(old, link));
		linkChanged(null, linkButton.getLink());
		Ui.box(this);
	}

	private BarSpanToolBox buildToggle(final BarSpan barSpan) {
		final FluentRadioButton radioButton = new FluentRadioButton(barSpan.getShortName())
				.userData(barSpan)
				.tooltip(Ui.tooltip(messageSource.get(barSpan.getName())))
				.removeStyleClass("radio-button")
				.styleClass("toggle-button", "primary", "small")
				.toggleGroup(toggleGroup);
		getChildren().add(radioButton);
		radioButton.setMinWidth(Region.USE_PREF_SIZE);
		radioButton.setMaxWidth(Region.USE_PREF_SIZE);
		radioButton.setMinHeight(Region.USE_PREF_SIZE);
		radioButton.setMaxHeight(Region.USE_PREF_SIZE);
		radioButton.selectedProperty().addListener((o, old, value) -> {
			if (value) {
				chartingView.barSpan(barSpan);
			}
		});
		return this;
	}

	@Override
	public Node getNode() {
		return this;
	}

	private void linkChanged(Link old, Link link) {
		if (null != old) {
			old.remove(linkStateConsumer);
		}
		if (null != link) {
			link.add(linkStateConsumer);
			linkStateConsumer.accept(link.getState());
		}
	}

	private void linkStateChanged(LinkState linkState) {
		Optional.ofNullable(linkState).ifPresent(value -> {
			final BarSpan barSpan = BarSpan.getByShortName(linkState.get("barSpan"));
			toggleGroup.getToggles().stream().filter(toggle -> Objects.equals(toggle.getUserData(), barSpan)).forEach(toggle -> toggle.setSelected(true));
		});
	}

}
