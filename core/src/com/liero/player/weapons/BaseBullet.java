package com.liero.player.weapons;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.liero.Box2D.Box2DBase;
import com.liero.Box2D.CollisionListenable;
import com.liero.terrain.ITerrain;

public abstract class BaseBullet extends Box2DBase implements IBullet {
	protected ITerrain terrain;
	protected Pool<IBullet> pool;
	
	public BaseBullet(ITerrain terrain) {
		this.terrain = terrain;
	}
	
	@Override
	public void setPool(Pool<IBullet> pool) {
		this.pool = pool;
	}
}
