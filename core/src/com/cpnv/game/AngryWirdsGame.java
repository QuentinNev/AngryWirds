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

import com.badlogic.gdx.math.Vector3;
import com.cpnv.game.Models.Bird;
import com.cpnv.game.Models.Scenery;

public class AngryWirdsGame extends ApplicationAdapter {
	SpriteBatch batch;
	Bird bird;
	Texture background;
	InputProcessor inputPro;
	Scenery scene;

	static final int WORLD_WIDTH = 1600;
	static final int WORLD_HEIGHT = 900;

	private OrthographicCamera camera;

	@Override
	public void create () {

		// INSTANTIATION
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		scene = new Scenery();
		bird = new Bird(new Vector2(100,500));
		camera = new OrthographicCamera();

		// SET PARAMS
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		batch.setProjectionMatrix(camera.combined);

		// INPUT MANAGEMENT
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchUp (int screenX, int screenY, int pointer, int button) {
				Vector3 realPress = unproject(screenX, screenY);
				if (bird.getSprite().getBoundingRectangle().contains(realPress.x, realPress.y)) bird.unFreeze();
				return true;
			}
		});
	}

	private Vector3 unproject(float x, float y) {
		return camera.unproject(new Vector3(x, y, 0));
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
		scene.add(bird);
		scene.draw(batch);
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