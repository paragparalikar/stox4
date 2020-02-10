package com.stox.fx.widget.search;

import java.util.function.BiPredicate;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentTextField;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.HasNode;
import com.stox.fx.widget.Icon;
import com.stox.util.StringUtil;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;

public class SearchBox<T> implements HasNode<Node>{

	private Searchable<T> searchable;
	private BiPredicate<T, String> matcher;
	private final FluentButton searchButton = new FluentButton(Icon.SEARCH).classes("icon", "last").defaultButton(true);
	private final FluentTextField textField = new FluentTextField().classes("first", "inverted").fullArea().onAction(e -> next());
	private final FluentHBox container = new FluentHBox(textField, searchButton).classes("box","search-box","primary").fillHeight(true);

	public SearchBox(@NonNull final Searchable<T> searchable, final BiPredicate<T, String> matcher) {
		this.searchable = searchable;
		this.matcher = null == matcher ? new DefaultMatcher<>() : matcher;
		textField.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (KeyCode.ENTER.equals(event.getCode())) {
				searchButton.fire();
			} else if (KeyCode.ESCAPE.equals(event.getCode())) {
				textField.clear();
			}
		});
	}
	
	private void next() {
		final String text = textField.getText();
		if (StringUtil.hasText(text)) {
			boolean found = false;
			for (int index = searchable.getSelectedIndex() + 1; index < searchable.size(); index++) {
				if (matcher.test(searchable.get(index), text)) {
					found = true;
					searchable.select(index);
					searchable.scrollTo(index);
					break;
				}
			}
			if(!found){
				for(int index = searchable.getSelectedIndex() - 1; index >= 0; index--){
					if (matcher.test(searchable.get(index), text)) {
						searchable.select(index);
						searchable.scrollTo(index);
						break;
					}
				}
			}
		}
	}

	public SearchBox<T> searchable(@NonNull final Searchable<T> searchable) {
		this.searchable = searchable;
		return this;
	}

	public SearchBox<T> matcher(final BiPredicate<T, String> matcher) {
		this.matcher = null == matcher ? new DefaultMatcher<>() : matcher;
		return this;
	}
	
	public String text() {
		return textField.getText();
	}
	
	public SearchBox<T> text(final String text){
		textField.setText(text);
		return this;
	}

	@Override
	public Node getNode() {
		return container;
	}
}
