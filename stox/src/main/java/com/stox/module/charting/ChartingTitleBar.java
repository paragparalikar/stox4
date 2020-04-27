package com.stox.module.charting;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.stox.fx.fluent.beans.binding.FluentStringBinding;
import com.stox.fx.widget.FxMessageSource;
import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.workbench.link.Link;
import com.stox.workbench.link.LinkButton;
import com.stox.workbench.link.LinkState;
import com.stox.workbench.module.ModuleTitleBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import lombok.Getter;
import lombok.NonNull;

public class ChartingTitleBar extends ModuleTitleBar {

	@Getter
	private final LinkButton linkButton = new LinkButton();
	private final Consumer<LinkState> linkStateConsumer;
	private final FxMessageSource messageSource;

	public ChartingTitleBar(@NonNull final FxMessageSource messageSource, final Consumer<LinkState> linkStateConsumer) {
		this.messageSource = messageSource;
		this.linkStateConsumer = linkStateConsumer;
		getTitleBar().append(Side.RIGHT, linkButton);
		linkButton.getLinkProperty().addListener(this::linkChanged);
	}

	ChartingTitleBar title(final BarSpan barSpan, final Scrip scrip) {
		if(Stream.<Object>of(barSpan, scrip).allMatch(Objects::nonNull)) {
			final ObservableValue<String> barSpanValue = messageSource.get(barSpan.getName());
			final ObservableValue<String> value = new FluentStringBinding(() -> title(scrip, barSpanValue), barSpanValue);
			title(value);
		}
		return this;
	}

	private String title(final Scrip scrip, final ObservableValue<String> barSpanValue) {
		return String.join(" - ", scrip.getName(), barSpanValue.getValue());
	}

	private void linkChanged(final ObservableValue<? extends Link> observable, final Link old, final Link link) {
		Optional.ofNullable(old).ifPresent(o -> o.remove(linkStateConsumer));
		Optional.ofNullable(link).ifPresent(l -> {
			link.add(linkStateConsumer);
			linkStateConsumer.accept(link.getState());
		});
	}

}

interface TitleWriter {

}
