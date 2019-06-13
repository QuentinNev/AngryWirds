package com.cpnv.game;

// LibGDX
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

// INPUTS
import com.badlogic.gdx.InputAdapter;

// DISPLAY
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


// VECTORS
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// GAME OBJECT
import com.cpnv.game.Models.Actor;
import com.cpnv.game.Models.Bird;
import com.cpnv.game.Models.Box;
import com.cpnv.game.Models.Pig;
import com.cpnv.game.Models.Scenery;
import com.cpnv.game.Models.Tnt;
import com.cpnv.game.Models.VocabularyProvider;
import com.cpnv.game.Models.Wasp;

import java.util.Random;

import static com.badlogic.gdx.graphics.Color.BLACK;

public class AngryWirdsGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Actor collider;
	private Texture background;
	private Scenery scene;
	private BitmapFont font;
	private VocabularyProvider voc;

	private Bird bird;
	private Vector2 birdDefaultPos;
	private Vector2 dragPos;

	private Wasp wasp;

	private static final int WORLD_WIDTH = 1600;
	private static final int WORLD_HEIGHT = 900;
	private static final int FLOOR_HEIGHT = 120;

	private Pig touchedPig;

	@Override
	public void create () {
		int boxNumber = 16;
		int tntNumer = 4;
		int tntScore = -50;
		int pigScore = 100;
		int boxStartPos = 400;
		int boxOffset = (int)Box.getSize().x;

		// INSTANTIATION
		batch = new SpriteBatch();
		background = new Texture("background.jpg");
		camera = new OrthographicCamera();
		scene = new Scenery(camera);
		voc = new VocabularyProvider();

		// font creation
		font = new BitmapFont();
		font.getData().setScale(3, 3);
		font.setColor(BLACK);

		touchedPig = null;

		birdDefaultPos = new Vector2(150, 300);
		bird = new Bird(birdDefaultPos);
		final Vector2 birdOffset = new Vector2(bird.getSprite().getWidth() / 2, bird.getSprite().getHeight() / 2);
		dragPos = new Vector2(0,0);

		wasp = new Wasp(new Vector2(500, 500));
		collider = null;
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
				scene.addNonStackableObjects(new Tnt(new Vector2(r.nextInt((WORLD_WIDTH - boxOffset) - boxStartPos) + boxStartPos,FLOOR_HEIGHT + boxOffset), tntScore));
			} catch (Exception e) {
				i--;
			}
		}

		// Place pigs
		for (int i = 0; i < tntNumer; i++) {
			Random r = new Random();
			try {
				scene.addNonStackableObjects(new Pig(new Vector2(r.nextInt((WORLD_WIDTH - boxOffset) - boxStartPos) + boxStartPos,FLOOR_HEIGHT + boxOffset), Double.toString(Math.random()), pigScore));
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
				if (bird.touched) {
					bird.touched = false;
					realPress.sub(birdDefaultPos.x, birdDefaultPos.y, 0);
					// This ugly stuff is necessary because Vector3 doesn't have a method to transform itself into a Vector2
					Vector2 result = new Vector2(birdDefaultPos.x - realPress.x, birdDefaultPos.y - realPress.y);
					bird.setForce(result);
					bird.unFreeze();
					voc.getLanguages();
				}

				touchedPig = null;
				return true;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
					Vector3 realPress = unproject(screenX, screenY);
					touchedPig = scene.checkTouchOnPigs(realPress.x, realPress.y);
					bird.touched = (bird.getSprite().getBoundingRectangle().contains(realPress.x, realPress.y));
				return true;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer){
				if (bird.touched) {
					Vector3 realPress = unproject(screenX, screenY);
					bird.getSprite().setPosition(realPress.x - birdOffset.x, realPress.y - birdOffset.y);
				}
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
		collider = scene.checkBirdCollisions(bird);

		checkCollision(collider);
		if(bird.getSprite().getY() <= FLOOR_HEIGHT && bird.getSprite().getX() > 400 || bird.getSprite().getX() < -100 || bird.getSprite().getX() > WORLD_WIDTH) {
			resetObjects();
		}
	}

	@Override
	public void render () {
		//----- Updating -----//
		update();

		//----- True rendering -----//
		batch.begin();
		batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
		scene.draw(batch);
		if(touchedPig != null){
			font.draw(batch, touchedPig.getWord(), touchedPig.getSprite().getX(), touchedPig.getSprite().getY() + 200);
		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	private void checkCollision(Actor collider){
		if (collider != null){
			// here we convert the class name into an int to be able to switch on it because android
			// SDK doesn't use JDK 1.7 it seems and it's needed to switch on string
			int colliderCode = collider.getClass().getSimpleName().hashCode();
			bird.freeze();
			switch (colliderCode) {
				// WASP or a good metal group
				case 2688711:
					//gameOver();
					resetObjects();
					break;

				// Tnt
				case 84250:
					resetObjects();
					break;

				// Box
				case 66987:
					resetObjects();
					break;

				case 80238:
					resetObjects();
					break;
			}
		}
		if (bird.getSprite().getX() > WORLD_WIDTH || bird.getSprite().getY() < 0) {
			bird.freeze();
		}
		this.collider = null;
	}

	private void gameOver() {
		System.exit(0);
	}
	private void resetObjects() {
		scene.resetGameObjects();
	}
}