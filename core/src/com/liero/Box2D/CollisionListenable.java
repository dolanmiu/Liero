package com.liero.Box2D;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface CollisionListenable {
	void collisionEvent(Fixture fixture);
}
