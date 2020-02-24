package com.stox.module.charting.unit.parent;

import com.stox.fx.widget.parent.GroupParentAdapter;

import javafx.scene.Group;
import javafx.scene.Node;
import lombok.NonNull;

public class GroupUnitParent extends GroupParentAdapter implements UnitParent<Node> {

	public GroupUnitParent(@NonNull final Group group) {
		super(group);
	}

}
