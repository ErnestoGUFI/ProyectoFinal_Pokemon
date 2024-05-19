package com.pokemon.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.game.screens.GameScreen;


public class MyPokemonGame extends Game {

    public SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));
    }
    
    @Override
    public void render () {
    	super.render();
    }

}

