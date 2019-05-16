package com.cpnv.game.Models;

import com.badlogic.gdx.math.Vector2;

public class Pig extends Actor {
    protected String word;
    private static final String spritePath = "pig.png";
    private static final Vector2 size = new Vector2(175, 150);

    public Pig(Vector2 position, String word){
        super(spritePath, position, size);
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void displayWord(){
        // onClick -> showWord
    }
}
