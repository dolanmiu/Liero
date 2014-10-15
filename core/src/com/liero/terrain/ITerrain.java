package com.liero.terrain;

import com.liero.IDynamicObject;


public interface ITerrain extends IDynamicObject {
	void createHole(float x, float y, int radius);
	void addToCreateHoleQueue(float x, float y, int radius);
}
