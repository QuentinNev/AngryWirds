package com.cpnv.game.Models;

import com.badlogic.gdx.math.Vector2;

public class Box extends MovingObject {
    private static final String spritePath = "block.png";
    private static final Vector2 size = new Vector2(75, 75);

    public Box(Vector2 position) {
        super(spritePath, position, size);
    }

    public static Vector2 getSize(){
        return size;
    }
}
