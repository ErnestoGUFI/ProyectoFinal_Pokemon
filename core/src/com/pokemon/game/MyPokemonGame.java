package com.pokemon.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.game.screens.FightScreen;
import com.pokemon.game.screens.GameScreen;


public class MyPokemonGame extends Game {

    public SpriteBatch batch;
    public static final int width = 1280;
    public static final int height = 720;
   	
    @Override
    public void create () {
    	//El batch se crea aqui pero se manda como parametro para las demas clases screen.
        batch = new SpriteBatch();
        
        if(iniciarPelea(false)) {
        	this.dispose();
        	this.setScreen(new FightScreen(this));
        }else {
        	this.setScreen(new GameScreen(this));
        }
    }
    
    @Override
    public void render () {
    	super.render();
    }
    
    public static boolean iniciarPelea(boolean flag) {
		System.out.println("puto");
		
    	return flag;
    }

}

