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

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private Viewport viewport;
    private Jugador jugador;
    private Controles controles;
    private Mapa[] mapas;
    private int mapaActualIndex;
    private boolean isPaused;
    private Pausa pausaScreen;

    private MyPokemonGame game;

    public GameScreen(MyPokemonGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(480, 220, camera);
        viewport.apply();

        jugador = new Jugador(game);
        controles = new Controles();

        mapas = new Mapa[] {
            new Mapa("mapa.tmx", camera),
            new Mapa("mapa2.tmx", camera),
            new Mapa("Casa.tmx", camera),
        };
        mapaActualIndex = 0;
        isPaused = false;
        pausaScreen = new Pausa(game);

        Gdx.input.setInputProcessor(controles);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (isPaused==false) {
                pause();
            } 
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isPaused) {
            camera.position.set(jugador.x, jugador.y, 0);
            camera.update();

            mapas[mapaActualIndex].render();

            jugador.update(controles, mapas[mapaActualIndex].getTiledMap(), this);

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            jugador.dibujar(game.batch, controles);
            game.batch.end();
        } else {
            pausaScreen.handleInput();

            game.batch.setProjectionMatrix(pausaScreen.pauseCamera.combined);
            game.batch.begin();
            pausaScreen.render(game.batch);
            game.batch.end();

            if (pausaScreen.isOptionSelected()) {
                resume();
               
                
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        pausaScreen.getPauseViewport().update(width, height); // Actualizar viewport de la pantalla de pausa
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
        pausaScreen.resetOptionSelected(); // Restablecer la opción seleccionada
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        jugador.dispose();
        pausaScreen.dispose();

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
