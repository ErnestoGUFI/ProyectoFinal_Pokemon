package com.pokemon.game;

<<<<<<< HEAD
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
=======
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

import Maps.Mapa;
import Player.Controles;
import Player.Jugador;

public class MyPokemonGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Jugador jugador;
    private Controles controles;
    private Mapa[] mapas;
    private int mapaActualIndex;
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(640, 300, camera);
        viewport.apply();
        
        jugador = new Jugador();
        controles = new Controles();

        
        mapas = new Mapa[] {
            new Mapa("mapa.tmx", camera),
            new Mapa("mapa2.tmx", camera),
            
        };
        mapaActualIndex = 0;

        Gdx.input.setInputProcessor(controles);
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
    }
    
    @Override
    public void render () {
<<<<<<< HEAD
    	super.render();
    }
}

=======
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(jugador.x, jugador.y, 0);
        camera.update();

        mapas[mapaActualIndex].render();
        
        jugador.update(controles, mapas[mapaActualIndex].getTiledMap(), this);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        jugador.dibujar(batch, controles);
        batch.end();
        
        System.out.println(jugador.x+" "+jugador.y);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        mapas[mapaActualIndex].resize(width, height);
    }

    @Override
    public void dispose () {
        batch.dispose();
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
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
