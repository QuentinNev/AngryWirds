package com.cpnv.game.Models;

import com.badlogic.gdx.math.Vector2;

public class Tnt extends MovingObject {
    private int scoreOnContact;
    private static final String spritePath = "tnt.png";
    private static final Vector2 size = new Vector2(75, 75);

    public Tnt(Vector2 position, int score) {
        super(spritePath, position, size);
        scoreOnContact = score;
    }
}
