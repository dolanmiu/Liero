package com.liero.terrain.quadtree;

import com.badlogic.gdx.math.Rectangle;

public interface IDataStructureQuadTreeNode {
	void split();
	void clear();
	boolean isLeaf();
	boolean isDeepestLevel();
	IDataStructureQuadTreeNode getNodeAtPosition(int x, int y);
	IDataStructureQuadTreeNode[] getNodes();
	Rectangle getBounds();
}
