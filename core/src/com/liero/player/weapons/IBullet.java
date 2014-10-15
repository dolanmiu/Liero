package com.liero.player.weapons;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.liero.Box2D.CollisionListenable;

public interface IBullet extends CollisionListenable, Poolable {
	void setPool(Pool<IBullet> pool);
}
