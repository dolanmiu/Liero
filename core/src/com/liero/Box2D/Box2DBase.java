package com.liero.Box2D;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class Box2DBase implements IBox2DBody {

	protected Body body;
	
	@Override
	public void clearBody() {
		Box2DHelper.removeBodySafely(body);	
		this.body = null;
	}
	
	@Override
	public void activate(float x, float y) {
		this.body.setTransform(x, y, 0);
		this.body.setActive(true);
	}

	@Override
	public void deactivate() {
		this.body.setActive(false);
	}

}
