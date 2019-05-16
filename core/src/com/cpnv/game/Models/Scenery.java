package com.cpnv.game.Models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public final class Scenery {
    private ArrayList<MovingObject> scene;

    public Scenery () {
        scene = new ArrayList<MovingObject>();
    }

    public void add(MovingObject movingObject) {
        scene.add(movingObject);
    }

    public void remove(MovingObject movingObject) { scene.remove(movingObject); }

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
