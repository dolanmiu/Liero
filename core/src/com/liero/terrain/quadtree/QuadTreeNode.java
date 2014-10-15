package com.liero.terrain.quadtree;

import com.badlogic.gdx.math.Rectangle;
import com.liero.Constants;
import com.liero.exceptions.NodeNotFoundException;
import com.liero.terrain.quadtreemodel.Box2DQuadTreeNode;

public abstract class QuadTreeNode implements IDataStructureQuadTreeNode {

	protected int level;
	private Rectangle bounds;
	protected Box2DQuadTreeNode[] nodes;
	
	public QuadTreeNode(int pLevel, Rectangle pBounds) {
		this.level = pLevel;
		this.bounds = pBounds;
		this.nodes = new Box2DQuadTreeNode[4];
	}
	
	public void clear() {
		for (int i = 0; i < this.nodes.length; i++) {
			if (this.nodes[i] != null) {
				this.nodes[i].clear();
				this.nodes[i] = null;
			}
		}
	}
	
	public abstract void split();
	
	public boolean isDeepestLevel() {
		return (this.level == Constants.MAX_TERRAIN_LEVELS);
	}
	
	public boolean isLeaf() {
		if (this.nodes[0] != null && this.nodes[1] != null && this.nodes[2] != null && this.nodes[3] != null ) {
			return false;
		}
		return true;
	}
	
	public Box2DQuadTreeNode getNode(int quadrant) {
		if (quadrant > 4 || quadrant < 0) {
			throw new ArrayIndexOutOfBoundsException("Provide a number between 1 and 4");
		}
		return this.nodes[quadrant];
	}
	
	public IDataStructureQuadTreeNode getNodeAtPosition(int x, int y) {
		if (isPositionWithinBounds(this.nodes[0].getBounds(), x, y)) return this.nodes[0];
		if (isPositionWithinBounds(this.nodes[1].getBounds(), x, y)) return this.nodes[1];
		if (isPositionWithinBounds(this.nodes[2].getBounds(), x, y)) return this.nodes[2];
		if (isPositionWithinBounds(this.nodes[3].getBounds(), x, y)) return this.nodes[3];
		throw new NodeNotFoundException(x, y);
	}
	
	private boolean isPositionWithinBounds(Rectangle bounds, int x, int y) {
		if (x >= bounds.x && x <= (bounds.x + bounds.width)) {
			if (y >= bounds.y && y <= (bounds.y + bounds.height)) {
				return true;
			}
		}
		return false;
	}
	
	public Box2DQuadTreeNode[] getNodes() {
		if (this.nodes[0] == null) return null;
		return this.nodes;
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	@Override
	public String toString() {
		return " Quad Tree Node: " + bounds.toString() + ". Is Leaf: " + isLeaf();
	}
}
