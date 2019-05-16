package com.cpnv.game.Models;

import com.badlogic.gdx.math.Vector2;

public abstract class MovingObject extends Actor {
    protected Vector2 velocity;
    protected final static float g = 9.81f;

    public MovingObject(String spritePath, Vector2 position, Vector2 size){
        super(spritePath, position, size);
        this.velocity = new Vector2(0,0);
    }

    public final void move(float dt) {
        accelerate(dt);
        sprite.translate(velocity.x, velocity.y);
    }

    public void accelerate(float dt){
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " at (" + sprite.getX() + ":" + sprite.getY() + "), moving to (" + this.velocity.x + ":" + this.velocity.y + ")";
    }

    public void setVelocity(Vector2 newVeloctiy) {
        velocity = newVeloctiy;
    }
}
