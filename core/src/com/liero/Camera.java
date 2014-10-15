package com.liero;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.liero.player.Targetable;

public class Camera extends OrthographicCamera {
	
	private Targetable target;
	private float lerp;
	private Rectangle bounds;

	public Camera(Rectangle bounds) {
		this.lerp = 3f;
		this.bounds = bounds;
	}
	
	public void update(float delta) {
		if (target != null) {
			Vector3 position = this.position;
			position.x += (target.getX() - position.x) * lerp * delta;
			position.y += (target.getY() - position.y) * lerp * delta;
		}
		
		/*if(this.position.x - this.viewportWidth / 2 < this.bounds.x)  {
			position.x = this.bounds.x + viewportWidth / 2;
		} else if(this.position.x + this.viewportWidth / 2 > (this.bounds.x + this.bounds.width)) {
			this.position.x = (this.bounds.x + this.bounds.width) - this.viewportWidth / 2;
		}
		
		if(this.position.y - this.viewportHeight / 2 < this.bounds.y) {
			this.position.y = this.bounds.y + this.viewportHeight / 2;
		} else if(position.y + viewportHeight / 2 > (this.bounds.y + this.bounds.height)) {
			this.position.y = (this.bounds.y + this.bounds.height) - this.viewportHeight / 2;
		}*/
		
		super.update();
	}
	
	public void setViewportDimensions(int width, int height) {
		viewportWidth = width;
		viewportHeight = height;
	}
	
	public void setTargetable(Targetable target) {
		this.target = target;
	}
	
	public void setLerp(float lerp) {
		this.lerp = lerp;
	}
	
	
}
