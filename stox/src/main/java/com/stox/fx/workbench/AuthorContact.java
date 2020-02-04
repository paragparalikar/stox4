package com.stox.fx.workbench;

import java.awt.Desktop;
import java.net.URI;

import com.stox.fx.fluent.scene.control.FluentButton;
import com.stox.fx.fluent.scene.control.FluentLabel;
import com.stox.fx.fluent.scene.layout.FluentHBox;
import com.stox.fx.widget.Icon;

import javafx.event.ActionEvent;
import javafx.scene.control.Tooltip;

public class AuthorContact extends FluentHBox{
	private static final String EMAIL = "parag.paralikar@gmail.com";
	private static final String LINKEDIN = "https://www.linkedin.com/in/paragparalikar/";

	private final FluentLabel authorLabel = new FluentLabel("Parag Paralikar").classes("primary").fullArea().focusTraversable(false);
	private final FluentButton phoneLabel = new FluentButton(Icon.PHONE).classes("icon", "primary").focusTraversable(false);
	private final FluentButton emailButton = new FluentButton(Icon.ENVELOPE).classes("icon", "primary").focusTraversable(false);
	private final FluentButton linkedInButton = new FluentButton(Icon.LINKEDIN).classes("icon", "primary").focusTraversable(false);
	private final FluentHBox buttonsBox = new FluentHBox(phoneLabel, emailButton, linkedInButton).focusTraversable(false);

	public AuthorContact() {
		classes("contact-box").children(authorLabel, buttonsBox).focusTraversable(false);
		phoneLabel.setTooltip(new Tooltip("+91-9960739342"));
		emailButton.setTooltip(new Tooltip(EMAIL));
		emailButton.addEventHandler(ActionEvent.ACTION, event -> {
			try {
				Desktop.getDesktop().browse(new URI("mailto:" + EMAIL + "?subject=Stox"));
			} catch (Exception e) {
			}
		});
		linkedInButton.setTooltip(new Tooltip(LINKEDIN));
		linkedInButton.addEventHandler(ActionEvent.ACTION, event -> {
			try {
				Desktop.getDesktop().browse(new URI(LINKEDIN));
			} catch (Exception e) {
			}
		});
	}

}
