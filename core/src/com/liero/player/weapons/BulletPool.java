package com.liero.player.weapons;

import com.badlogic.gdx.utils.Pool;
import com.liero.Box2D.IBox2DBody;

public class BulletPool extends Pool<IBullet> {

	private IBulletFactory factory;
	
	public BulletPool(IBulletFactory factory) {
		this.factory = factory;
	}
	
	@Override
	protected IBullet newObject() {
		IBullet bullet = factory.create();
		((IBox2DBody) bullet).createBody();
		bullet.setPool(this);
		return bullet;
	}


}
