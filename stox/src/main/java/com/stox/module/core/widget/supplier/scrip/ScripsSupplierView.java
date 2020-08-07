package com.stox.module.core.widget.supplier.scrip;

import java.util.Collection;
import java.util.function.Supplier;

import com.stox.fx.widget.HasNode;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.HasName;

import javafx.scene.Node;

public interface ScripsSupplierView extends HasName, HasNode<Node>, Supplier<Collection<Scrip>>{

}
