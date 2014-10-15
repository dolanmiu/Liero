package com.liero.player;

import com.liero.IDynamicObject;

public interface ICrosshair extends IDynamicObject {
	void setPosition(float xOffset, float yOffset);

	void setAngle(float angle);
}
