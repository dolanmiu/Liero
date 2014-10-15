package com.liero.player;

import com.liero.IDynamicObject;

public interface IPawn extends IDynamicObject, IControllable, Targetable {
	void setFireDistance(float fireDistance);
}
