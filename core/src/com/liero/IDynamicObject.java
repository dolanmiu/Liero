package com.liero;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface IDynamicObject {
	void draw(Batch batch);

	void update(float delta);
}
