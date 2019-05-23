package com.cpnv.game;

// LibGDX
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// GAME OBJECT
import com.cpnv.game.Models.Bird;
import com.cpnv.game.Models.Box;
import com.cpnv.game.Models.Scenery;
import com.cpnv.game.Models.Tnt;
import com.cpnv.game.Models.Wasp;

import java.util.Random;

public class AngryWirdsGame extends ApplicationAdapter {
	SpriteBatch batch;
	Bird bird;
	Wasp wasp;
	Texture background;
	Scenery scene;

	public static final int WORLD_WIDTH = 1600;
	public static final int WORLD_HEIGHT = 900;
	public static final int FLOOD_HEIGHT = 120;

	private OrthographicCamera camera;

	@Override
	public void create () {
		int boxNumber = 16;
		int tntNumer = 4;
		int tntScore = -50;
		int boxStartPos = 400;
		int boxOffset = (int)Box.getSize().x;

		// INSTANTIATION
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		scene = new Scenery();
		camera = new OrthographicCamera();

		bird = new Bird(new Vector2(100,500));
		wasp = new Wasp(new Vector2(500, 500));
        scene.add(bird);
        scene.add(wasp);

        // Place boxes
		for(int i = 0; i < boxNumber; i++) {
			try {
				scene.addNonStackableObjects(new Box(new Vector2(boxStartPos + (boxOffset * i), 120)));
			} catch (Exception e) {
				Gdx.app.log("ANGRY", e + "");
			}
		}

		// Place Tnts
		for (int i = 0; i < tntNumer; i++) {
			Random r = new Random();
			try {
				scene.addNonStackableObjects(new Tnt(new Vector2(r.nextInt(WORLD_WIDTH - boxStartPos) + boxStartPos,FLOOD_HEIGHT + boxOffset), -50));
			} catch (Exception e) {
				i--;
			}
		}

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
		//----- Updating -----//
		update();

		//----- True rendering -----//
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		scene.draw(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}