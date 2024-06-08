package com.pokemon.game.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

import Audio.Musica;
import Fights.FightManager;
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
    public Musica musicaMapa;
    private MyPokemonGame game;
    private String playerName;
    
    private FightManager fightManager;
    
    public GameScreen(MyPokemonGame game, String playerName) {
    	 this.game = game;
         this.playerName = playerName;
         musicaMapa = new Musica();     
         fightManager = new FightManager(game, playerName); // Pasar el nombre del jugador
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(480, 220, camera);
        viewport.apply();

        jugador = new Jugador(game);
        controles = new Controles();

        mapas = new Mapa[] {
            new Mapa("Maps/mapa.tmx", camera),
            new Mapa("Maps/mapa2.tmx", camera),
            new Mapa("Maps/Casa.tmx", camera),
        };
        mapaActualIndex = 0;
        isPaused = false;
        pausaScreen = new Pausa(game);

        Gdx.input.setInputProcessor(controles);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (!isPaused) {
                pause();
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isPaused && !fightManager.isPelea()) {
            camera.position.set(jugador.x, jugador.y, 0);
            camera.update();
            
            musicaMapa.playMapMusic();

            mapas[mapaActualIndex].render();

            fightManager.setPelea(jugador.update(controles, mapas[mapaActualIndex].getTiledMap(), this));

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            jugador.dibujar(game.batch, controles);
            game.batch.end();
        } else {
            if (fightManager.isPelea()) {
            	fightManager.startBattle();
            	fightManager.renderBattle(delta);
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
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        pausaScreen.getPauseViewport().update(width, height);
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
        pausaScreen.resetOptionSelected();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        jugador.dispose();
        pausaScreen.dispose();
        musicaMapa.dispose();
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

    public void stopMapMusic() {
        musicaMapa.stopMapMusic();
    }
}
