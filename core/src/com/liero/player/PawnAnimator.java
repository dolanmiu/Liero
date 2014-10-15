package com.liero.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PawnAnimator implements IPawnAnimator {

	private Animation[] animations;
	private int lookDirection;
	private FaceDirection faceDirection;
	private float stateTime;
	private TextureRegion currentFrame;
	private boolean isMoving;

	private final int MAX_LOOK_DIRECTIONS = 6;
	private final int SPRITE_TILE_WIDTH = 32;
	private final int SPRITE_TILE_HEIGHT = 18;
	
	private float x;
	private float y;

	public enum FaceDirection {
		LEFT, RIGHT;
	}

	public PawnAnimator(Texture spriteSheet) {
		this.animations = new Animation[7];
		this.lookDirection = 4;
		// TextureRegion textureRegion = new TextureRegion(spriteSheet, 1, 0,
		// 671, 36);
		TextureRegion[][] tr = TextureRegion.split(spriteSheet,
				SPRITE_TILE_WIDTH, SPRITE_TILE_HEIGHT);
		for (int i = 0; i < this.animations.length; i++) {
			this.animations[i] = new Animation(1 / 3f, tr[0][i], tr[0][i + 7],
					tr[0][i + (7 * 2)]);
		}

		this.currentFrame = animations[0].getKeyFrame(this.stateTime, true);
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(this.currentFrame, this.x, this.y);
	}

	@Override
	public void update(float delta) {
		if (this.isMoving) {
			this.stateTime += delta;
			updateFaceDirection();
			this.currentFrame = animations[this.lookDirection].getKeyFrame(
					this.stateTime, true);
		}
	}

	@Override
	public void setLookDirection(float angle) {
		float angleRange = (float) (Math.PI - (Math.PI / 5));
		angle -= 0.5f * Math.PI;
		if (angle < 0) {
			angle += 2 * Math.PI;
		}
		
		float angleSectorLength = (float) (angleRange / MAX_LOOK_DIRECTIONS);
		int lookDirectionIndex = 0;

		if (angle > 0 && angle < angleRange) {
			this.faceDirection = FaceDirection.LEFT;

			float currentAngle = 0;

			while (currentAngle <= (float) (Math.PI)) {
				if ((angle > 0 && angle < angleSectorLength) || currentAngle > angle) {
					setLookDirection(lookDirectionIndex);
					return;
				}
				currentAngle += angleSectorLength;
				lookDirectionIndex++;
			}
		}

		if (angle > (2 * Math.PI - angleRange) && angle < Math.PI * 2) {
			this.faceDirection = FaceDirection.RIGHT;

			float currentAngle = (float) (Math.PI * 2);

			while (currentAngle > (float) (2 * Math.PI - angleRange)) {
				if ((angle < Math.PI * 2 && angle > (2 * Math.PI - angleSectorLength)) || currentAngle < angle) {
					setLookDirection(lookDirectionIndex);
					return;
				}
				currentAngle -= angleSectorLength;
				lookDirectionIndex++;
			}
		}

	}

	private void setLookDirection(int lookDirection) {
		if (lookDirection > MAX_LOOK_DIRECTIONS) {
			this.lookDirection = MAX_LOOK_DIRECTIONS;
			return;
		}

		if (lookDirection < 0) {
			this.lookDirection = 0;
			return;
		}

		this.lookDirection = lookDirection;
		updateFaceDirection();
		this.currentFrame = animations[this.lookDirection].getKeyFrame(
				this.stateTime, true);
	}

	@Override
	public int getLookDirection() {
		return this.lookDirection;
	}

	private void updateFaceDirection() {
		TextureRegion frame = animations[this.lookDirection].getKeyFrame(
				this.stateTime, true);
		if (this.faceDirection == FaceDirection.LEFT) {
			if (!frame.isFlipX()) {
				frame.flip(true, false);
			}
		} else {
			if (frame.isFlipX()) {
				frame.flip(true, false);
			}
		}
	}

	@Override
	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

}
