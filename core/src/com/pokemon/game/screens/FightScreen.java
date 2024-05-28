package com.pokemon.game.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

import Maps.Mapa;
import Player.Controles;
import Player.Jugador;

public class FightScreen implements Screen{
    private Controles controles;
    MyPokemonGame game;
    
    //Texturas de los sprites de la primera escena.
    private Texture entrenadorSprite;
    private Texture fondoInicioBatalla;
    private Texture pokemonEnemigoSprite;
    private Texture vsSprite;
    //Texturas de los sprites de la batalla en si.
    private Texture fondoBatalla;
    
    //Animaciones
    int ySpriteJugador=0;
    int ySpriteEnemigo=369;
    
    //Timer que llevara el tiempo del juego.
    Timer tiempo;
    int seg=0;
    
    
    public FightScreen(MyPokemonGame game) {
    	 this.game = game;
    	 entrenadorSprite = new Texture("entrenadorpelea.png");
    	 fondoInicioBatalla = new Texture("fondoInicioBatalla.png");
    	 pokemonEnemigoSprite = new Texture("squirtleSprite.png");
    	 vsSprite = new Texture("vsSprite.png");
    	 fondoBatalla = new Texture("fondoBatalla.png");
    	 tiempo = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seg++;
			}
    	 });
    }
    
	@Override
	public void show() {
        controles = new Controles();
        Gdx.input.setInputProcessor(controles);
        tiempo.start();
        
	}

	@Override
	public void render(float delta) {
		
        //Toda la aniamcion de inicio de batalla dura 5 segundos, se realizara ese proceso utilizando el siguiente if.
        if(seg<2) {
    		Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        	game.batch.begin();
        	//Dibujar las cosas.
            game.batch.draw(fondoInicioBatalla, 0, 0,1280,720); //Este no sera dinamico
            game.batch.draw(entrenadorSprite, 50, ySpriteJugador, 400, 400); // Este no sera dinamico
            
            //Dinamico para cada pokemon o entrenador, guardar un width y height o Guardar todos los sprites con mismo width y height
            game.batch.draw(pokemonEnemigoSprite, 770, ySpriteEnemigo, 500, 400);
            
            game.batch.draw(vsSprite, 580, 300, 150, 170); //Este no sera dinamico
            
            //Animaciones de los sprites del entrenador enemigo y jugador.
            
            if(ySpriteJugador<69) {
            	ySpriteJugador++;
            }
            
            if(ySpriteEnemigo>300) {
            	ySpriteEnemigo--;
            	System.out.println(ySpriteEnemigo);
            }
        }
        
        if(seg>=2) {
        	game.batch.begin();
        	Gdx.gl.glClearColor(240, 240, 240, 1);
        	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
        	game.batch.draw(fondoBatalla, 48, 47, 1178, 630);
        	
        }
        
        
        
        
        
        game.batch.end();
        
    		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
        game.batch.dispose();
	}
	
}
