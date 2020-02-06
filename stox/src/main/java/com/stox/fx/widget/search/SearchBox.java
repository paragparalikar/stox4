package com.stox.fx.widget.search;

import java.util.function.BiPredicate;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentTextField;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Icon;
import com.stox.util.StringUtil;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SearchBox<T> extends FluentHBox {

	private Searchable<T> searchable;
	private BiPredicate<T, String> matcher;
	private final FluentButton searchButton = new FluentButton(Icon.SEARCH).classes("icon", "last").defaultButton(true);
	private final FluentTextField textField = new FluentTextField().classes("first", "inverted").fullArea().onAction(e -> next());

	public SearchBox(final Searchable<T> searchable, final BiPredicate<T, String> matcher) {
		this.searchable = searchable;
		this.matcher = null == matcher ? new DefaultMatcher<>() : matcher;
		children(textField, searchButton).classes("box","search-box").fillHeight(true);
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

	public void setSearchable(final Searchable<T> searchable) {
		this.searchable = searchable;
	}

	public void setMatcher(final BiPredicate<T, String> matcher) {
		this.matcher = null == matcher ? new DefaultMatcher<>() : matcher;
	}
	
	public FluentTextField getTextField() {
		return textField;
	}
}
