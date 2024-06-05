package com.pokemon.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.game.screens.GameScreen;
import com.pokemon.game.screens.MainScreen;


public class MyPokemonGame extends Game {

    public SpriteBatch batch;
    public static final int width = 1280;
    public static final int height = 720;
   	
    @Override
    public void create () {
    	//El batch se crea aqui pero se manda como parametro para las demas clases screen.
        batch = new SpriteBatch();
        setScreen(new MainScreen(this));
        

    }
    
    public static BitmapFont getDefaultFont() {
    	// Devuelve una nueva instancia de la fuente predeterminada
    	return new BitmapFont(); 
    }
    
    @Override
    public void render () {
    	super.render();
    }

}

