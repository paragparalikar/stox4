package com.stox.fx.fluent.scene;

import javafx.scene.Parent;

public interface IFluentParent<T extends Parent & IFluentParent<T>> extends IFluentNode<T> {

}
