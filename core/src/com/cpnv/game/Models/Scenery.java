package com.cpnv.game.Models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public final class Scenery {
    private ArrayList<MovingObject> scene;

    public Scenery () {
        scene = new ArrayList<MovingObject>();
    }

    public void add(MovingObject object) {
        scene.add(object);
    }

    public void addNonStackableObjects(MovingObject object) throws Exception  {
        // Check before adding to the object if the space is free
        Rectangle rectangle = object.getSprite().getBoundingRectangle();
        for (MovingObject item: scene) if (rectangle.overlaps(item.getSprite().getBoundingRectangle())) throw new Exception("Something is already there");
        scene.add(object);
    }

    public void remove(MovingObject object) { scene.remove(object); }

    public void draw(SpriteBatch batch) {
        for (MovingObject item: scene) {
            item.draw(batch);
        }
    }

    public void move(float dt){
        for (MovingObject item: scene) {
            item.move(dt);
        }
    }
}
