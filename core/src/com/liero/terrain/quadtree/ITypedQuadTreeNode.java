package com.liero.terrain.quadtree;

import com.liero.terrain.quadtreemodel.Box2DQuadTreeNode.QuadType;

public interface ITypedQuadTreeNode extends IDataStructureQuadTreeNode {
	boolean isQuadTreesSameType();
	QuadType getType();
	void setType(QuadType type);
}
