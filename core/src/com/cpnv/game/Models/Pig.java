package com.cpnv.game.Models;

import com.badlogic.gdx.math.Vector2;

public class Pig extends Actor {
    protected String word;
    private int scoreOnHit;
    private static final String spritePath = "bear.png";
    private static final Vector2 size = new Vector2(75, 100);

    public Pig(Vector2 position, String word, int score){
        super(spritePath, position, size);
        this.word = word;
        this.scoreOnHit = score;
    }

    public String getWord() {
        return word;
    }

    public void displayWord(){
        // onClick -> showWord
    }

    @Override
    public final void move(float dt){}
}
