package com.cpnv.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bird extends MovingObject {
    private static final Vector2 size = new Vector2(175, 150);
    private static final String spritePath = "bean.png";
    public boolean touched = false;

    public Bird(Vector2 position) {
        super(spritePath, position, size);
        freezed = true;
    }

    @Override
    public void accelerate(float dt) {
        if (!freezed) velocity.y += -g * dt * 5;
    }

    @Override
    public void unFreeze() {
        freezed = false;
    }

    @Override
    public void freeze() {
        freezed = true;
        velocity = new Vector2(0,0);
    }

    @Override
    public void reset() {
        super.reset();
        freeze();
    }

    public void setForce(Vector2 force){
        velocity.x = force.x / 10;
        velocity.y = force.y / 20;
    }
}
