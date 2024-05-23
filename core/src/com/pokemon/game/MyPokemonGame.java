package com.pokemon.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.game.screens.GameScreen;


public class MyPokemonGame extends Game {

    public SpriteBatch batch;

    @Override
    public void create () {
    	//El batch se crea aqui pero se manda como parametro para las demas clases screen.
        batch = new SpriteBatch();
        this.setScreen(new GameScreen(this));
    }
    
    @Override
    public void render () {
    	super.render();
    }
}

