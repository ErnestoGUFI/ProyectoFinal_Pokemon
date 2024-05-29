package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

import Maps.Mapa;
import Player.Controles;
import Player.Jugador;

public class GameScreen implements Screen{
    private OrthographicCamera camera;
    private Viewport viewport;
    private Jugador jugador;
    private Controles controles;
    private Mapa[] mapas;
    private int mapaActualIndex;
    
    int tileWidth = 40;
    int tileHeight = 40;
    MyPokemonGame game;
    
    public GameScreen(MyPokemonGame game) {
    	 this.game = game;
    }
    
	@Override
	public void show() {
		camera = new OrthographicCamera();
        viewport = new FitViewport(640, 300, camera);
        viewport.apply();
        
        jugador = new Jugador();
        controles = new Controles();

        
        mapas = new Mapa[] {
            new Mapa("mapa.tmx", camera),
            new Mapa("mapa2.tmx", camera),
            new Mapa("Casa.tmx", camera),
            
        };
        mapaActualIndex = 0;

        Gdx.input.setInputProcessor(controles);
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(jugador.x, jugador.y, 0);
        camera.update();

        mapas[mapaActualIndex].render();
        
        jugador.update(controles, mapas[mapaActualIndex].getTiledMap(), this);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        jugador.dibujar(game.batch, controles);
        game.batch.end();
        
        //System.out.println(jugador.x+" "+jugador.y);
        
    		
	}

	@Override
	public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        mapas[mapaActualIndex].resize(width, height);
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
        jugador.dispose();
        
        for (Mapa mapa : mapas) {
            mapa.dispose();
        }
	}
	
    public void cambiarMapa(int nuevoIndice, float nuevaPosX, float nuevaPosY) {
        if (nuevoIndice >= 0 && nuevoIndice < mapas.length) {
            mapaActualIndex = nuevoIndice;
            jugador.setPosition(nuevaPosX, nuevaPosY);
        }
    }
}
