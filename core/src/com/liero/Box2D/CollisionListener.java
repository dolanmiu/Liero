package com.liero.Box2D;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionListener implements ContactListener {
    
    public CollisionListener() {
    }

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        
        Object collidedObjectA = fixtureA.getBody().getUserData();
        if (collidedObjectA instanceof CollisionListenable) {
        	((CollisionListenable) collidedObjectA).collisionEvent(fixtureB);
        }
        
        Object collidedObjectB = fixtureB.getBody().getUserData();
        if (collidedObjectB instanceof CollisionListenable) {
        	((CollisionListenable) collidedObjectB).collisionEvent(fixtureA);
        }
        
        //Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	}

	@Override
	public void endContact(Contact contact) {
        //Fixture fixtureA = contact.getFixtureA();
        //Fixture fixtureB = contact.getFixtureB();
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
