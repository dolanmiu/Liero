package com.liero.terrain.quadtreemodel;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liero.Box2D.Box2DBase;
import com.liero.Box2D.Box2DHelper;
import com.liero.Box2D.IBox2DBody;

public class Box2DTerrainBlock extends Box2DBase implements IBox2DBody {

	private float width;
	private float height;

	public Box2DTerrainBlock(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public float getHalfWidth() {
		return this.width * 0.5f;
	}
	
	public float getHalfHeight() {
		return this.height * 0.5f;
	}

	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;

		FixtureDef blockFixture = new FixtureDef();

		// width = width / Constants.METRES_TO_PIXEL_RATIO;
		// height = height / Constants.METRES_TO_PIXEL_RATIO;

		PolygonShape boxPoly = new PolygonShape();
		// * Constants.METRES_TO_PIXEL_RATIO;
		// * Constants.METRES_TO_PIXEL_RATIO;
		// set the anchor point to be the corner of the sprite. Dividing by 2 will centre
		boxPoly.setAsBox(getHalfWidth(), getHalfHeight());

		blockFixture.shape = boxPoly;

		this.body = Box2DHelper.getWorld().createBody(bodyDef);
		this.body.createFixture(blockFixture);
		boxPoly.dispose();
		
		this.body.setUserData(this);
		this.body.setActive(false);
	}

}
