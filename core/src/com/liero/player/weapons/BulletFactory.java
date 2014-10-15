package com.liero.player.weapons;

import java.lang.reflect.InvocationTargetException;

import com.liero.terrain.ITerrain;

public class BulletFactory<T extends IBullet> implements IBulletFactory {
	
	private ITerrain terrain;
	private Class<T> bulletClass;
	
	public BulletFactory(Class<T> bulletClass, ITerrain terrain) {
		this.terrain = terrain;
		this.bulletClass = bulletClass;
	}

	@Override
	public T create() {
		try {
			return bulletClass.getDeclaredConstructor(ITerrain.class).newInstance(this.terrain);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
