package com.liero.Box2D;

public interface IBox2DBody {
	void clearBody();
	void createBody();
	void activate(float x, float y);
	void deactivate();
}
