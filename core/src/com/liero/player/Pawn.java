package com.liero.player;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Pool;
import com.liero.Constants;
import com.liero.Box2D.Box2DBase;
import com.liero.Box2D.Box2DHelper;
import com.liero.player.weapons.BulletPool;
import com.liero.player.weapons.IBullet;

public class Pawn extends Box2DBase implements IPawn {
	
	private Vector2 velocity;
	private boolean isInAir;
	private IPawnAnimator animator;
	private float firingAngle;
	private float fireDistance;
	private ICrosshair crosshair;
	private List<Pool<IBullet>> bulletPools;
	
	private final float walkSpeed = 10 * 60;
	
	public Pawn(List<Pool<IBullet>> bulletPool, IPawnAnimator animator, ICrosshair crosshair) {
		this.velocity = new Vector2(0, 0);
		this.fireDistance = 50;
		this.animator = animator;
		this.crosshair = crosshair;
		this.bulletPools = bulletPool;
	}
	
	@Override
	public void draw(Batch batch) {
		crosshair.draw(batch);
		animator.draw(batch);
	}
	
	@Override
	public void update(float delta) {
		updatePosition(delta);
		this.crosshair.setAngle(this.firingAngle);
		this.crosshair.setPosition(this.body.getPosition().x, this.body.getPosition().y);
		this.crosshair.update(delta);
		this.animator.setPosition(this.body.getPosition().x, this.body.getPosition().y);
		this.animator.update(delta);
		//checkInSceneBounds();
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.linearDamping = 1;
		
		FixtureDef fixtureDef = new FixtureDef();
		
		CircleShape boxPoly = new CircleShape();
	    float halfWidth = 0.5f * 0.5f * Constants.METRES_TO_PIXEL_RATIO;
	    float halfHeight = 1 * 0.5f * Constants.METRES_TO_PIXEL_RATIO;
	    boxPoly.setRadius(halfWidth);//(halfWidth, halfHeight);
	    
	    fixtureDef.shape = boxPoly;
	    this.body = Box2DHelper.getWorld().createBody(bodyDef);
	    this.body.createFixture(fixtureDef);

		this.body.setUserData(this);
		this.body.setActive(false);
	}
	
	private void updatePosition(float delta) {
		body.applyForceToCenter(velocity.x, 0, true);
	}
	
	private void checkInSceneBounds() {
		if (this.body.getPosition().y <= 10) {
			this.body.getPosition().y = 10;
		}
		
		if (getX() <= 0) {
			this.body.getPosition().x = 0;
		}
	}
	
	private Vector2 calculateFirePosition(float angle, float distance) {
		Vector2 v = new Vector2();
		v.x = this.body.getPosition().x + distance * (float)Math.cos(angle);
		v.y = this.body.getPosition().y + distance * (float)Math.sin(angle);
		return v;
	}
	
	private float calculateFireAngle(float x, float y) {
		float angle = (float)Math.atan(y/x);
		if (x < 0 && y < 0) {
			angle += Math.PI;
		}
		
		if (x < 0 && y >= 0) {
			//angle -= Math.PI;
			angle += Math.PI;
		}
		
		if (x > 0 && y <= 0) {
			angle += 2 * Math.PI;
		}
		//System.out.println(angle);
		return angle;
	}
	
	@Override
	public void moveLeft() {
		this.velocity.x = -walkSpeed;
		this.animator.setIsMoving(true);
	}

	@Override
	public void moveRight() {
		this.velocity.x = walkSpeed;
		this.animator.setIsMoving(true);
	}

	@Override
	public void jump() {
		this.velocity.y = 600;
	}

	@Override
	public void stopMovement() {
		velocity.x = 0;
		this.animator.setIsMoving(false);
	}

	@Override
	public float getX() {
		return this.body.getPosition().x;
	}

	@Override
	public float getY() {
		return this.body.getPosition().y;
	}

	@Override
	public void fire() {
		Vector2 pos = calculateFirePosition(this.firingAngle, this.fireDistance);
		Box2DBase bullet = (Box2DBase) this.bulletPools.get(0).obtain();
		bullet.activate(pos.x, pos.y);
	}

	@Override
	//Mouse version
	public void aim(float x, float y) {
		float xDirection = x - Gdx.graphics.getWidth() / 2;
		float yDirection = -(y - Gdx.graphics.getHeight() / 2);
		//System.out.println(xDirection + ", " + yDirection);
		this.firingAngle = calculateFireAngle(xDirection, yDirection);
		this.animator.setLookDirection(this.firingAngle);
	}
	
	@Override 
	public void setFireDistance(float fireDistance) {
		this.fireDistance = fireDistance;
	}
}
