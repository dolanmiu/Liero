package com.liero.player;

public interface IControllable {
	void moveLeft();

	void moveRight();

	void jump();

	void stopMovement();

	void fire();

	void aim(float x, float y);

	// PC commands maybe shouldnt be implemented
}
