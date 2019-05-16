package com.cpnv.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Actor extends com.badlogic.gdx.scenes.scene2d.Actor {
    protected Sprite sprite;
    protected Vector2 size;

    public Actor(String spritePath, Vector2 position, Vector2 size) {
        sprite = new Sprite(new Texture(spritePath));
        sprite.setBounds(position.x, position.y, size.x, size.y);
        sprite.setOrigin(position.x, position.y / 2);
}

    public Actor(){}

    @Override
    public String toString() {
        // Actor at (82:249)
        return getClass().getSimpleName() + " at ("+ sprite.getX() +":" + sprite.getY() +")";
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
