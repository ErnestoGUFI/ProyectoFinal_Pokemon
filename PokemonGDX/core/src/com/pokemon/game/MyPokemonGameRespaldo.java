package com.pokemon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Player.Controles;
import Player.Jugador;

public class MyPokemonGameRespaldo extends ApplicationAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Jugador jugador;
    private Controles controles;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    
    int tileWidth = 40;
    int tileHeight = 40;

    @Override
    public void create () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        // el viewport que se menciona mas abajo para hacerle resize a la camara de el personaje
        viewport = new FitViewport(640, 360, camera);
        viewport.apply();
        
        //se crea un jugador y un controles
        jugador = new Jugador();
        controles = new Controles();

        // Cargar el mapa Tiled
        TmxMapLoader mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("mapaPrincipal.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(controles);
    }
    
    

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        //la posicion de la camara esta en la coordenada x e y de el jugador y se va actualizando con el movimiento
        camera.position.set(jugador.getX(), jugador.getY(), 0);
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
        
        jugador.update(controles, tiledMap, tileWidth, tileHeight);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        jugador.dibujar(batch, controles);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //el viewport se encarga de hacer mas pequeña la camara y seguir al personaje, esto se hace dentro de el create donde se crea un FitViewport
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void dispose () {
    	//eliminacion de recursos para el rendimiento
        batch.dispose();
        jugador.dispose();
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }
}

