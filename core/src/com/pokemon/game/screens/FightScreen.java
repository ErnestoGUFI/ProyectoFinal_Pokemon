package com.pokemon.game.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    //Objeto de la clase ShapeRenderer que dibuja formas.
    ShapeRenderer sr;
    
    
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
    	 
    	 sr = new ShapeRenderer();
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
            game.batch.end();
        }
        
        if(seg>=2) {
        	game.batch.begin();
        	Gdx.gl.glClearColor(240, 240, 240, 1);
        	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
        	//Dibujar utilizando el batch.
        	game.batch.draw(fondoBatalla, 48, 47, 1178, 630);
        	
        	game.batch.end();
        	
        	//Dibujar utilizando el ShapeRenderer
        	sr.begin(ShapeRenderer.ShapeType.Filled);
        	sr.setColor(new Color(217/255f, 217/255f, 217/255f, 0.8f)); //Se divide entre 255 ya que el constructor no acepta valores mayores a 1.
        	sr.rect(48, 47, 1178, 150);
        	
        	roundRect(sr, 90, 63, 520, 120, 20, new Color(175/255f, 175/255f, 175/255f,0.8f));
        	
        	//Rectangulos del ataque.
        	roundRect(sr,695,130,210,52,10,new Color(255/255f, 0/255f, 0/255f,0.8f));
        	roundRect(sr,965,130,210,52,10,new Color(255/255f, 0/255f, 0/255f,0.8f));
        	roundRect(sr,695,58,210,52,10,new Color(255/255f, 0/255f, 0/255f,0.8f));
        	roundRect(sr,965,58,210,52,10,new Color(255/255f, 0/255f, 0/255f,0.8f));
        	
        	//Rectangulo de ataque seleccionado.
        	roundRect(sr,698,135,204,42,6,new Color(248/255f, 168/255f, 176/255f,0.8f)); //Este ira cambiando.
        	
        	sr.end();
        }
        
        
        
        
        
        
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
        if(seg>=2) sr.dispose();
	}
	
	public void roundRect(ShapeRenderer sr, int x, int y, int width, int height, int radio, Color color) {
		sr.setColor(color);
		
		sr.rect(x, y, width, height);
		
		//Esquina inferior izquierda
		sr.arc(x, y + radio, radio, 180, 90);
		//Esquina superior izquierda
		sr.arc(x, y + height - radio, radio, 90, 90);
		
		//Esquina inferior derecha
		sr.arc(x + width, y + radio, radio, 270, 90);
		//Esquina superior derecha
		sr.arc(x + width, y + height - radio, radio, 0, 90);
		
		sr.rect(x - radio, y + radio, radio, height - (2*radio));
		sr.rect(x + width, y + radio, radio, height - (2*radio));
	
	}
	
}
