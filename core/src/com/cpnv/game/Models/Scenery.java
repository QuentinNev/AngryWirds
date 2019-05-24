package com.cpnv.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public final class Scenery {
    private ArrayList<Actor> scene;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public Scenery (OrthographicCamera camera) {
        scene = new ArrayList<Actor>();
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void add(Actor object) {
        scene.add(object);
    }

    public void addNonStackableObjects(Actor object) throws Exception  {
        // Check before adding to the object if the space is free
        Rectangle rectangle = object.getSprite().getBoundingRectangle();
        for (Actor item: scene) if (rectangle.overlaps(item.getSprite().getBoundingRectangle())) throw new Exception("Something is already there");
        scene.add(object);
    }

    public void remove(Actor object) { scene.remove(object); }

    public void draw(SpriteBatch batch) {
        for (Actor item: scene) {
            item.draw(batch);
        }
    }

    public void move(float dt){
        for (Actor item: scene) {
            item.move(dt);
        }
    }
    public Actor checkBirdCollisions(Bird bird) {
        for (Actor object: scene){
            if (object != bird && bird.getSprite().getBoundingRectangle().overlaps(object.getSprite().getBoundingRectangle())) return object;
        }
        return null;
    }

    public void resetGameObjects () {
        for(Actor item: scene) {
            item.reset();
        }
    }

    public void showHitboxes() {
        for(Actor item: scene) {
            Sprite sprite = item.getSprite();
            shapeRenderer.setColor(0,1,0,1);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
            shapeRenderer.end();
            Gdx.app.log("ANGRY", "Wow shapeRenderer c'est vraiment useless !");
        }
    }

    // Only called on touchDown()
    public String checkTouchOnPigs(float x, float y) {
        for(Actor item: scene) {
            if (item.getClass() == Pig.class && item.getSprite().getBoundingRectangle().contains(x, y)) {
                Pig p = (Pig)item;
                return p.getWord();
            }
        }
        return null;
    }
}
