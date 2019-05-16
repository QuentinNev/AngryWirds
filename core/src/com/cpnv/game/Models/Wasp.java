package com.cpnv.game.Models;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Wasp extends MovingObject {
    private static final String spritePath = "wasp.png";
    private static final Vector2 size = new Vector2(175, 150);

    public Wasp(Vector2 position) {
        super(spritePath, position, size);
    }

    @Override
    public void accelerate(float dt) {
        int min = -10;
        int max = 10;

        Random r = new Random();
        int random = r.nextInt(max - min) + min;

        velocity.x = random * dt * 1000;
        velocity.y = random * dt * 1000;

    }
}
