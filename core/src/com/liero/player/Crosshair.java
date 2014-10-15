package com.liero.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Crosshair implements ICrosshair {
	
	private float distance;
	private Vector2 crosshairPosition;
	private ShapeRenderer crosshairRenderer;
	private float xOffset;
	private float yOffset;
	private float angle;
	
	public Crosshair(float distance, ShapeRenderer crosshairRenderer) {
		this.distance = distance;
		this.crosshairRenderer = crosshairRenderer;
		this.crosshairPosition = new Vector2();
	}
	
	@Override
	public void draw(Batch batch) {
		batch.end();
		crosshairRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		crosshairRenderer.begin(ShapeType.Line);
		crosshairRenderer.setColor(new Color(1, 0, 0, 1));
		crosshairRenderer.rect(this.xOffset + this.crosshairPosition.x, this.yOffset + this.crosshairPosition.y, 5, 5);
		crosshairRenderer.end();
		batch.begin();
	}
	
	@Override
	public void update(float delta) {
		calculateFirePosition(crosshairPosition, this.angle);
	}
	
	private void calculateFirePosition(Vector2 v, float angle) {
		v.x = this.distance * (float)Math.cos(angle);
		v.y = this.distance * (float)Math.sin(angle);
	}
	
	@Override
	public void setPosition(float xOffset, float yOffset) {
		//Checks
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	@Override
	public void setAngle(float angle) {
		this.angle = angle;
	}
}
