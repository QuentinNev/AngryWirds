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
import com.cpnv.game.Models.Wasp;

public class AngryWirdsGame extends ApplicationAdapter {
	SpriteBatch batch;
	Bird bird;
	Wasp wasp;
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
		camera = new OrthographicCamera();
		bird = new Bird(new Vector2(100,500));
		wasp = new Wasp(new Vector2(500, 500));
		scene.add(bird);
		scene.add(wasp);

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
		scene.move(dt);
	}

	@Override
	public void render () {
		//Gdx.app.log("ANGRY", wasp.getX() + " // " + wasp.getY());
		//----- Updating -----//

		update();

		//----- True rendering -----//


		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
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