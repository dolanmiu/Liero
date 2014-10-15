package com.liero.Box2D;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.liero.exceptions.WorldNotFoundException;

public class Box2DHelper {
	
	private static World world;
	private static Queue<IBox2DBody> tobeClearedQueue = new LinkedList<IBox2DBody>();
	private static Queue<IBox2DBody> tobeDeactivatedQueue = new LinkedList<IBox2DBody>();
	
	public static void setWorld(World newWorld) {
		world = newWorld;
	}
	
	public static World getWorld() {
		if (world == null) {
			throw new WorldNotFoundException();
		}
		return world;
	}
	
	public static void removeBodySafely(Body body) {
		if (world == null) {
			throw new WorldNotFoundException();
		}
	    //to prevent some obscure c assertion that happened randomly once in a blue moon
		if (body == null) return;
	    final Array<JointEdge> list = body.getJointList();
	    while (list.size > 0) {
	    	world.destroyJoint(list.get(0).joint);
	    }
	    // actual remove
	    world.destroyBody(body);
	}
	
	public static BodyDef createBody(BodyType type, FixtureDef... fixtureDefs) {
		return null;
	}
	
	public static void update(float timeStep, int velocityIterations, int positionIterations) {
		if (world == null) {
			throw new WorldNotFoundException();
		}
		world.step(timeStep, velocityIterations, positionIterations);
		
		while (!tobeClearedQueue.isEmpty()) {
			tobeClearedQueue.remove().clearBody();
		}
		
		while(!tobeDeactivatedQueue.isEmpty()) {
			tobeDeactivatedQueue.remove().deactivate();
		}
	}
	
	public static void addToDisposeQueue(IBox2DBody body) {
		tobeClearedQueue.add(body);
	}
	
	public static void dispose() {
		if (world == null) {
			throw new WorldNotFoundException();
		}
		world.dispose();
	}

	public static void addToDeactivateQueue(IBox2DBody body) {
		tobeDeactivatedQueue.add(body);
	}
}
