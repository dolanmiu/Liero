package com.liero.terrain;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.liero.terrain.quadtree.IDataStructureQuadTreeNode;
import com.liero.terrain.quadtree.ITypedQuadTreeNode;
import com.liero.terrain.quadtreemodel.Box2DQuadTreeNode;
import com.liero.terrain.quadtreemodel.Box2DQuadTreeNode.QuadType;

public abstract class BaseTerrain extends Sprite implements ITerrain {
	
    private ShapeRenderer maskRenderer;
    protected ITypedQuadTreeNode quadTreeRoot;
	private Queue<Vector3> terrainDestructionQueue;

    
	public BaseTerrain(Texture texture, int x, int y, int size) {
		super(texture, x, y, size, size);
		this.getTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);

		this.quadTreeRoot = new Box2DQuadTreeNode(0, new Rectangle(x, y, size, size), null);
		//quadTreeRoot.setType(QuadType.DIRT);
		this.maskRenderer = new ShapeRenderer();
		this.terrainDestructionQueue = new LinkedList<Vector3>();
	}
	
	@Override
	public void draw(Batch batch) {
		//remove
		/*debugRenderer.begin(ShapeType.Point);
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.point(60, 40, 0);
		debugRenderer.end();*/
		batch.end();
		
		//2. clear our depth buffer with 1.0
		Gdx.gl.glClearDepthf(1f);
		Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
				
		//3. set the function to LESS
		Gdx.gl.glDepthFunc(GL20.GL_LESS);
				
		//4. enable depth writing
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
				
		//5. Enable depth writing, disable RGBA color writing 
		Gdx.gl.glDepthMask(true);
		Gdx.gl.glColorMask(false, false, false, false);
		
		//6. Render mask
		drawTree(batch, quadTreeRoot);
		
		batch.begin();
		Gdx.gl.glColorMask(true, true, true, true);
		
		//9. Make sure testing is enabled.
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		
		//10. Now depth discards pixels outside our masked shapes
		Gdx.gl.glDepthFunc(GL20.GL_EQUAL);
		
		//push to the batch*/	
		super.draw(batch);	
		batch.end();
		
		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
		batch.begin();
	}
	
	private void drawTree(Batch batch, ITypedQuadTreeNode node) {
		if (node.isLeaf()) {
			//System.out.println(node.toString());
			if (node.getType() != QuadType.AIR) {
				maskRenderer.setProjectionMatrix(batch.getProjectionMatrix());
				maskRenderer.begin(ShapeType.Filled);
				maskRenderer.setColor(new Color(1, 0, 0, 1));
				maskRenderer.rect(node.getBounds().getX(), node.getBounds().getY(), node.getBounds().getWidth(), node.getBounds().getHeight());
				maskRenderer.end();
			} else {
				/*maskRenderer.setProjectionMatrix(batch.getProjectionMatrix());
				maskRenderer.begin(ShapeType.Line);
				maskRenderer.setColor(new Color(0, 1, 0, 1));
				maskRenderer.rect(node.getBounds().getX(), node.getBounds().getY(), node.getBounds().getWidth(), node.getBounds().getHeight());
				maskRenderer.end();*/
			}
		} else {
			for (IDataStructureQuadTreeNode currentNode : node.getNodes()) {
				drawTree(batch, (ITypedQuadTreeNode)currentNode);
			}
		}
	}
	
	private void updateCreateHoleCalls() {
		while (!this.terrainDestructionQueue.isEmpty()) {
			Vector3 holeParameters = this.terrainDestructionQueue.remove();
			createHole(holeParameters.x, holeParameters.y, (int)holeParameters.z);
		}
	}
	
	public void addToCreateHoleQueue(float x, float y, int radius) {
		this.terrainDestructionQueue.add(new Vector3(x, y, radius));
	}
	
	@Override
	public void update(float delta) {
		updateCreateHoleCalls();
	}
	
	@Override
	public abstract void createHole(float x, float y, int radius);
}
