package com.liero.terrain.quadtreemodel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.liero.terrain.BaseTerrain;
import com.liero.terrain.quadtree.IDataStructureQuadTreeNode;
import com.liero.terrain.quadtree.ITypedQuadTreeNode;
import com.liero.terrain.quadtreemodel.Box2DQuadTreeNode.QuadType;

public class BlockTerrain extends BaseTerrain {
	
	public BlockTerrain(Texture texture, int x, int y, int size) {
		super(texture, x, y, size);
	}
	
	@Override
	public void createHole(float x, float y, int radius) {
		if (!checkIfPointInBounds(x, y)) return;
		int Left = (int)x - radius;
		int Right = Left + radius * 2;
		int Top = (int)y - radius;
		int Bottom = Top + radius * 2;
		
		for (int j = Top; j <= Bottom; ++j)
		{
		    for (int k = Left; k <= Right; ++k)
		    {
		        double dist = Math.pow(x - k, 2.0) + Math.pow(y - j, 2.0);
		        if (dist <= Math.pow(radius, 2))
		        {
		        	ITypedQuadTreeNode node = recursiveSplit(k, j, this.quadTreeRoot);
		    		if (node == null) continue;
		    		node.setType(QuadType.AIR);
		        }
		    }
		}
		
		checkForQuadTreeReCombination();
	}
	
	private ITypedQuadTreeNode recursiveSplit(int x, int y, ITypedQuadTreeNode node) {
		if (node.isLeaf()) {
			if (node.getType() == QuadType.AIR) return node;
			if (node.isDeepestLevel()) {
				return node;
			}
			node.split();
		}
		
		if (!checkIfPointInBounds(x, y)) return null;
		
		ITypedQuadTreeNode currentNode = (ITypedQuadTreeNode)node.getNodeAtPosition(x, y);
		ITypedQuadTreeNode splitNode = recursiveSplit(x, y, currentNode);
		return splitNode;
	}
	
	private void checkForQuadTreeReCombination() {
		recursiveQuadTreeRecombination(this.quadTreeRoot);
	}
	
	private boolean checkIfPointInBounds(float x, float y) {
		Rectangle bounds = this.quadTreeRoot.getBounds();
		if (x > bounds.x && x < bounds.x + bounds.width) {
			if (y > bounds.y && y < bounds.y + bounds.height) {
				return true;
			}
		}
		return false;
	}
	
	private void recursiveQuadTreeRecombination(ITypedQuadTreeNode node) {
		IDataStructureQuadTreeNode[] currentNodes = node.getNodes();
		
		if (currentNodes == null) return;
		
		if (currentNodes[0].isLeaf() && currentNodes[1].isLeaf() && currentNodes[2].isLeaf() && currentNodes[3].isLeaf()) {
			if (node.isQuadTreesSameType()) {
				QuadType currentType = ((ITypedQuadTreeNode)node.getNodes()[0]).getType();
				node.clear();
				node.setType(currentType);
			}
			return;
		}
		
		for (IDataStructureQuadTreeNode currentNode : currentNodes) {
			if (!currentNode.isLeaf()) recursiveQuadTreeRecombination((ITypedQuadTreeNode)currentNode);
		}
	}
}
