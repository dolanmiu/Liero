package com.liero.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;
import com.liero.Camera;
import com.liero.Box2D.Box2DHelper;
import com.liero.Box2D.CollisionListener;
import com.liero.Box2D.IBox2DBody;
import com.liero.player.Crosshair;
import com.liero.player.IPawn;
import com.liero.player.InputController;
import com.liero.player.Pawn;
import com.liero.player.PawnAnimator;
import com.liero.player.PawnFactory;
import com.liero.player.weapons.BulletFactory;
import com.liero.player.weapons.BulletPool;
import com.liero.player.weapons.IBullet;
import com.liero.player.weapons.SimpleBullet;
import com.liero.terrain.ITerrain;
import com.liero.terrain.quadtreemodel.BlockTerrain;

public class TestLevel implements Screen {

	private SpriteBatch batch;
	private Camera camera;
    private IPawn pawn;
    private InputController controller;
    private Box2DDebugRenderer debugRenderer;
    private ITerrain terrain;
    private PawnFactory pawnFactory;

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.batch.setProjectionMatrix(camera.combined);
		camera.update(delta);
		
		Box2DHelper.update(1 / 60f, 8, 3);
		
		this.batch.begin();
		this.terrain.draw(this.batch);
		this.pawn.draw(this.batch);
		this.batch.end();

		this.pawn.update(delta);
		this.terrain.update(delta);
		debugRenderer.render(Box2DHelper.getWorld(), camera.combined);
		
		//System.out.println(Gdx.graphics.getFramesPerSecond());
	}
	
	private void handleInput() {
		if (Gdx.input.isTouched()) {
			Vector3 worldCoord = this.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			//this.terrain.createHole(worldCoord.x, worldCoord.y, 60);
			//WorldManager.getInstance().getTerrain().createHole(60, 40, 100);
			//WorldManager.getInstance().getTerrain().createHole(300, 456, 30);
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.setViewportDimensions(width / 2, height / 2);
		//camera.setViewportDimensions(width, height);
	}

	@Override
	public void show() {
		World world = new World(new Vector2(0, 9.81f), true);
		CollisionListener collisionListener = new CollisionListener();
		world.setContactListener(collisionListener);
		Box2DHelper.setWorld(world);
		
		this.terrain = new BlockTerrain(new Texture("badlogic.jpg"), 0, 0, 1000);
		
		this.debugRenderer = new Box2DDebugRenderer();
		
		this.batch = new SpriteBatch();
		
		this.pawnFactory = new PawnFactory(new PawnAnimator(new Texture("playerAtlas.png")), new Crosshair(50, new ShapeRenderer()), new BulletPool(new BulletFactory<SimpleBullet>(SimpleBullet.class, this.terrain)));
		this.pawn = this.pawnFactory.create();
		((IBox2DBody)this.pawn).createBody();
		((IBox2DBody)this.pawn).activate(60, 40);
		
		this.camera = new Camera(new Rectangle(0, 0, 5000, 1000));
		this.camera.setTargetable(pawn);
		this.controller = new InputController(pawn);
		
		Gdx.input.setInputProcessor(this.controller);
	}
	
	 

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
		Box2DHelper.getWorld().dispose();
	}

}
