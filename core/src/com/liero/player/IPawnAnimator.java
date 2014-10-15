package com.liero.player;

import com.liero.IDynamicObject;

public interface IPawnAnimator extends IDynamicObject {
	void setLookDirection(float angle);

	int getLookDirection();

	void setIsMoving(boolean isMoving);

	void setPosition(float x, float y);

}
