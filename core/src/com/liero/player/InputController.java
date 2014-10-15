package com.liero.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {

	private IControllable controllable;
	
	public InputController(IControllable controllable) {
		this.controllable = controllable;
	}
	
	public void setControllable(IControllable controllable) {
		this.controllable = controllable;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			this.controllable.moveLeft();
			break;
		case Keys.RIGHT:
			this.controllable.moveRight();
			break;
		case Keys.Z:
			this.controllable.fire();
			break;
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			this.controllable.stopMovement();
			break;
		case Keys.RIGHT:
			this.controllable.stopMovement();
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		this.controllable.fire();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.controllable.aim(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
