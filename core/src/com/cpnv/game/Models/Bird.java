package com.cpnv.game.Models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Bird extends MovingObject {
    private float throwStrength;
    private static final Vector2 size = new Vector2(175, 150);
    private static final String spritePath = "bean.png";

    public Bird(Vector2 position) {
        super(spritePath, position, size);
        throwStrength = 1000f;
        freezed = true;
    }

    @Override
    public void accelerate(float dt) {
        if (!freezed) {
            velocity.x = throwStrength * dt;
            velocity.y += -g * dt * 5;
        }
    }

    @Override
    public void unFreeze() {
        freezed = false;
        velocity.y = 10f;
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
}
