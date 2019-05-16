package com.cpnv.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import com.cpnv.game.Models.Bird;

public class AngryWirdsGame extends ApplicationAdapter {
	SpriteBatch batch;
	Bird bird;
	Texture background;
	InputProcessor inputPro;

	static final int WORLD_WIDTH = 1600;
	static final int WORLD_HEIGHT = 900;

	private OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.jpg");


		bird = new Bird(new Vector2(100,500));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		batch.setProjectionMatrix(camera.combined);

		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchUp (int screenX, int screenY, int pointer, int button) {
				Gdx.app.log("ANGRY", screenX + " : " + screenY);
				Gdx.app.log("ANGRY", "" + bird.getSprite().getBoundingRectangle());
				if (bird.getSprite().getBoundingRectangle().contains(screenX, screenY)) bird.unFreeze();
				return true;
			}
		});
	}

	private void update() {
		float dt = Gdx.graphics.getDeltaTime();
		bird.move(dt);
	}

	@Override
	public void render () {
		//----- Updating -----//

		update();

		//----- Real rendering -----//

		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		bird.draw(batch);
		batch.end();

		/*
		float dt = Gdx.graphics.getDeltaTime();
		Gdx.app.log("Anrgy","Deltatime is : " + dt);
		*/
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}