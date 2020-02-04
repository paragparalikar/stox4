package com.stox.fx.fluent.stage;

import java.util.List;

import com.stox.fx.fluent.Area;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public interface IFluentStage<T extends Stage & IFluentStage<T>> extends Area<T>, IFluentWindow<T>{

	default StageStyle style() {
		return getThis().getStyle();
	}
	
	default T style(StageStyle style) {
		getThis().initStyle(style);
		return getThis();
	}
	
	default Modality modality() {
		return getThis().getModality();
	}
	
	default T modality(Modality modality) {
		getThis().initModality(modality);
		return getThis();
	}
	
	default Window owner() {
		return getThis().getOwner();
	}
	
	default T owner(Window owner) {
		getThis().initOwner(owner);
		return getThis();
	}
	
	default Scene scene() {
		return getThis().getScene();
	}
	
	default T scene(Scene scene) {
		getThis().setScene(scene);
		return getThis();
	}
	
	default boolean fullScreen() {
		return getThis().isFullScreen();
	}
	
	default T fullScreen(boolean fullScreen) {
		getThis().setFullScreen(fullScreen);
		return getThis();
	}
	
	default String title() {
		return getThis().getTitle();
	}
	
	default T title(String title) {
		getThis().setTitle(title);
		return getThis();
	}
	
	default boolean iconified() {
		return getThis().isIconified();
	}
	
	default T iconified(boolean iconified) {
		getThis().setIconified(iconified);
		return getThis();
	}
	
	default boolean maximized() {
		return getThis().isMaximized();
	}
	
	default T maximized(boolean maximized) {
		getThis().setMaximized(maximized);
		return getThis();
	}
	
	default boolean alwaysOnTop() {
		return getThis().isAlwaysOnTop();
	}
	
	default T alwaysOnTop(boolean alwaysOnTop) {
		getThis().setAlwaysOnTop(alwaysOnTop);
		return getThis();
	}
	
	default boolean resizable() {
		return getThis().isResizable();
	}
	
	default T resizable(boolean resizable) {
		getThis().setResizable(resizable);
		return getThis();
	}
	
	default double minWidth() {
		return getThis().getMinWidth();
	}
	
	default T minWidth(double minWidth) {
		getThis().setMinWidth(minWidth);
		return getThis();
	}
	
	default double maxWidth() {
		return getThis().getMaxWidth();
	}
	
	default T maxWidth(double maxWidth) {
		getThis().setMaxWidth(maxWidth);
		return getThis();
	}
	
	default double minHeight() {
		return getThis().getMinHeight();
	}
	
	default T minHeight(double minHeight) {
		getThis().setMinHeight(minHeight);
		return getThis();
	}
	
	default double maxHeight() {
		return getThis().getMaxHeight();
	}
	
	default T maxHeight(double maxHeight) {
		getThis().setMaxHeight(maxHeight);
		return getThis();
	}
	
	default KeyCombination fullScreenExitKeyCombination() {
		return getThis().getFullScreenExitKeyCombination();
	}
	
	default T fullScreenExitKeyCombination(KeyCombination fullScreenExitKeyCombination) {
		getThis().setFullScreenExitKeyCombination(fullScreenExitKeyCombination);
		return getThis();
	}
	
	default String fullScreenExitHint() {
		return getThis().getFullScreenExitHint();
	}
	
	default T fullScreenExitHint(String fullScreenExitHint) {
		getThis().setFullScreenExitHint(fullScreenExitHint);
		return getThis();
	}
	
	default T show_() {
		getThis().show();
		return getThis();
	}
	
	default ObservableList<Image> icons(){
		return getThis().getIcons();
	}
	
	default T toFront_() {
		getThis().toFront();
		return getThis();
	}
	
	default T toBack_() {
		getThis().toBack();
		return getThis();
	}
	
	default T close_() {
		getThis().close();
		return getThis();
	}

	@Override
	default double width() {
		return getThis().getWidth();
	}

	@Override
	default double height() {
		return getThis().getHeight();
	}

	@Override
	default double x() {
		return getThis().getX();
	}

	@Override
	default double y() {
		return getThis().getY();
	}
	
	@Override
	default Cursor cursor() {
		return null == scene() ? null : scene().getCursor();
	}

	@Override
	default T cursor(Cursor cursor) {
		scene().setCursor(cursor);
		return getThis();
	}
	
	@Override
	default Rectangle2D maxBounds() {
		final Stage stage = getThis();
		final List<Screen> screens = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(),
				stage.getHeight());
		final Screen screen = screens.stream().filter(s -> s.getVisualBounds()
				.contains(stage.getX() + stage.getWidth() / 2, stage.getY() + stage.getHeight() / 2)).findFirst().get();
		return screen.getVisualBounds();
	}
	
	@Override
	default T bounds(double x, double y, double width, double height) {
		return getThis().x(x).y(y).width(width).height(height);
	}
	
	@Override
	default <E extends Event> T addHandler(EventType<E> eventType, EventHandler<? super E> eventHandler) {
		getThis().addEventHandler(eventType, eventHandler);
		return getThis();
	}
	
	@Override
	default <E extends Event> T removeHandler(EventType<E> eventType, EventHandler<? super E> eventHandler) {
		getThis().removeEventHandler(eventType, eventHandler);
		return getThis();
	}
	
	@Override
	default <E extends Event> T addFilter(EventType<E> eventType, EventHandler<? super E> eventHandler) {
		getThis().addEventFilter(eventType, eventHandler);
		return getThis();
	}
	
	@Override
	default <E extends Event> T removeFilter(EventType<E> eventType, EventHandler<? super E> eventHandler) {
		getThis().removeEventFilter(eventType, eventHandler);
		return getThis();
	}
}
