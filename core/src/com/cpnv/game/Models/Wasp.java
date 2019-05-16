package com.cpnv.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Wasp extends MovingObject {
    private static final String spritePath = "bean.png";
    private static final Vector2 size = new Vector2(175, 150);

    private static final int WORLD_WIDTH = 1600;
    private static final int WORLD_HEIGHT = 900;

    public Wasp(Vector2 position) {
        super(spritePath, position, size);
    }

    @Override
    public void accelerate(float dt) {
        int min = 0;
        int max = 3;

        Random r = new Random();
        int randomX = r.nextInt(max);
        int randomY = r.nextInt(max);

        velocity.x += (sprite.getX() > WORLD_WIDTH / 2) ? -randomX : randomX;
        velocity.y += (sprite.getY() > WORLD_HEIGHT / 2) ? -randomY : randomY;

        if (velocity.x + velocity.y > 40) { velocity.x /= 10; velocity.y /= 10; }
    }
}
