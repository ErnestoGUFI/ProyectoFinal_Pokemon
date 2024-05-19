package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

import Player.Controles;
import Player.Jugador;

public class GameScreen implements Screen{
    private OrthographicCamera camera;
    private Viewport viewport;
    private Jugador jugador;
    private Controles controles;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    
    int tileWidth = 40;
    int tileHeight = 40;
    MyPokemonGame game;
    
    public GameScreen(MyPokemonGame game) {
    	 this.game = game;
    }
    
	@Override
	public void show() {
        camera = new OrthographicCamera();
        // esta madre es la camara que sigue al monito
        viewport = new FitViewport(640, 360, camera);
        viewport.apply();
        
        
        
        jugador = new Jugador();
        controles = new Controles();

        // Cargar el mapa Tiled
        TmxMapLoader mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("mapaPrincipal.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(controles);
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        //esta madre es la que sigue al monito
        camera.position.set(jugador.getX(), jugador.getY(), 0);
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
        jugador.update(controles, tiledMap, tileWidth, tileHeight);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        jugador.dibujar(game.batch, controles);
        game.batch.end();
    		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
        tiledMap.dispose();
        tiledMapRenderer.dispose();
	}
	
}
