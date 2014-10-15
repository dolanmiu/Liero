package com.liero.terrain.polygonmodel;

import com.badlogic.gdx.graphics.Texture;
import com.liero.terrain.BaseTerrain;

public class PolygonTerrain extends BaseTerrain {
	
	public PolygonTerrain(Texture texture, int x, int y, int size) {
		super(texture, x, y, size);
	}

	@Override
	public void createHole(float x, float y, int radius) {
		// TODO Auto-generated method stub
		
	}
}
