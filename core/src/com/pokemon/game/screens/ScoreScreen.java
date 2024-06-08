package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.pokemon.game.MyPokemonGame;

import Audio.Musica;
import DataBase.DataBase;

import java.util.List;

public class ScoreScreen extends ScreenAdapter {
    private MyPokemonGame game;
    private OrthographicCamera camera;
    private Texture background;
    private BitmapFont font;
    private List<String[]> scores;
    private Musica musicaMenu;

    public ScoreScreen(MyPokemonGame game) {
        this.game = game;
        musicaMenu = new Musica();
        musicaMenu.playMenuMusic();
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        background = new Texture("Screen/BackgroundMenu.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon_GB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        generator.dispose();

        scores = DataBase.getAllScores(); // Obtener todos los puntajes de la base de datos

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Keys.ESCAPE) {
                    game.setScreen(new MainScreen(game)); 
                    musicaMenu.stopMenuMusic(); 
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        drawScores(300); // Dibujar los puntajes en la pantalla

        game.batch.end();
    }

    // Método para dibujar los puntajes en la pantalla
    private void drawScores(float startY) {
        float y = startY;
        for (String[] score : scores) {
            String text = score[0] + ": " + score[1]; // Obtener el nombre y el puntaje
            GlyphLayout layout = new GlyphLayout(font, text);
            font.draw(game.batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, y); // Dibujar el texto
            y -= 30; // Descender la posición Y para el siguiente puntaje
        }
    }

   
    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }
}
