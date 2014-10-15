package com.liero.terrain.quadtreemodel;

import com.badlogic.gdx.math.Rectangle;
import com.liero.terrain.quadtree.ITypedQuadTreeNode;
import com.liero.terrain.quadtree.QuadTreeNode;

public class Box2DQuadTreeNode extends QuadTreeNode implements ITypedQuadTreeNode {

	private QuadType type;
	private Box2DTerrainBlock body;

	public enum QuadType {
		DIRT, AIR
	}

	/*
	 * Constructor
	 */
	public Box2DQuadTreeNode(int pLevel, Rectangle pBounds, Box2DQuadTreeNode parent) {
		super(pLevel, pBounds);		
		setType(QuadType.DIRT);
	}

	/*
	 * Clears the quadtree
	 */
	public void clear() {
		super.clear();
		if (this.type == QuadType.DIRT)
			createBody(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
	}

	/*
	 * Splits the node into 4 subnodes
	 */
	@Override
	public void split() {
		if (isDeepestLevel()) return;
		
		float subWidth = (getBounds().getWidth() / 2);
		float subHeight = (getBounds().getHeight() / 2);
		float x = getBounds().getX();
		float y = getBounds().getY();

		if (nodes[0] == null) nodes[0] = new Box2DQuadTreeNode(this.level+1, new Rectangle(x + subWidth, y, subWidth, subHeight), this);
		if (nodes[1] == null) nodes[1] = new Box2DQuadTreeNode(this.level+1, new Rectangle(x, y, subWidth, subHeight), this);
		if (nodes[2] == null) nodes[2] = new Box2DQuadTreeNode(this.level+1, new Rectangle(x, y + subHeight, subWidth, subHeight), this);
		if (nodes[3] == null) nodes[3] = new Box2DQuadTreeNode(this.level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight), this);
		
		clearBody();
	}
	
	public void setType(QuadType type) {
		this.type = type;
		switch(type) {
		case AIR:
			clearBody();
			break;
		case DIRT:
			if (this.body != null) break;
			createBody(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
			break;
		default:
			break;
		}
	}
	
	public QuadType getType() {
		return this.type;
	}
	
	
	private void createBody(float x, float y, float width, float height) {
		Box2DTerrainBlock block = new Box2DTerrainBlock(width, height);
		block.createBody();
		block.activate(x + block.getHalfWidth(), y + block.getHalfHeight());
		this.body = block;
	}
	
	private void clearBody() {
		if (this.body == null) return;
		this.body.clearBody();
		this.body = null;
	}
	
	public boolean isQuadTreesSameType() {
		QuadType type = this.nodes[0].getType();
		for (Box2DQuadTreeNode node : this.nodes) {
			if (node.getType() != type) return false; 
		}
		return true;
	}
}
