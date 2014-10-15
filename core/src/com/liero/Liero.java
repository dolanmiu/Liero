package com.liero;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liero.screens.MainMenu;

public class Liero extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create() {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		setScreen(new MainMenu());//new Splash());
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void pause () {
		super.pause();
	}

	@Override
	public void resume () {
		super.resume();
	}

	@Override
	public void render() {
		super.render();
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}
	
	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
	}
}
