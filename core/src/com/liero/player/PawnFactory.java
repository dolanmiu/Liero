package com.liero.player;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.utils.Pool;
import com.liero.player.weapons.IBullet;

public class PawnFactory {

	private IPawnAnimator animator;
	private ICrosshair crosshair;
	private List<Pool<IBullet>> pools;
	
	public PawnFactory(IPawnAnimator animator, ICrosshair crosshair, Pool<IBullet>... pools) {
		this.animator = animator;
		this.crosshair = crosshair;
		this.pools = Arrays.asList(pools);	
	}
	
	public IPawn create() {
		return new Pawn(pools, animator, crosshair);
	}
}
