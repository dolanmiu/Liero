package com.liero.player.weapons;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Pool;
import com.liero.Constants;
import com.liero.Box2D.Box2DHelper;
import com.liero.terrain.ITerrain;
import com.liero.terrain.quadtreemodel.Box2DTerrainBlock;

public class SimpleBullet extends BaseBullet {
	
	public SimpleBullet(ITerrain terrain) {
		super(terrain);
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.bullet = true;
		
		FixtureDef blockFixture = new FixtureDef();
		
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.1f * Constants.METRES_TO_PIXEL_RATIO);

	    blockFixture.shape = circleShape;
	    
		this.body = Box2DHelper.getWorld().createBody(bodyDef);
		this.body.createFixture(blockFixture);
		circleShape.dispose();
		this.body.setUserData(this);
		this.body.setActive(false);
	}

	@Override
	public void collisionEvent(Fixture fixture) {
		if (fixture.getBody().getUserData() instanceof Box2DTerrainBlock) {
			float x = this.body.getPosition().x;
			float y = this.body.getPosition().y;
			this.terrain.addToCreateHoleQueue(x, y, 30);
			pool.free(this);
			Box2DHelper.addToDeactivateQueue(this);
		}
	}

	@Override
	public void reset() {

	}
}
