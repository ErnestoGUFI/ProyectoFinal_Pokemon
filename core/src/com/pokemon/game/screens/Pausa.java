package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;
import Audio.Musica;
import DataBase.DataBase;
import Fights.FightManager;

public class Pausa {
    OrthographicCamera pauseCamera;
    private Viewport pauseViewport;
    private BitmapFont font;
    private BitmapFont selectedFont;
    private Texture background;
    private float selectedY;
    private MyPokemonGame game;
    private boolean isOptionSelected;
    private FightManager fightManager;
    private Musica musica;
    private boolean isMuted;

    public Pausa(MyPokemonGame game, FightManager fightManager, Musica musica) {
        this.game = game;
        this.fightManager = fightManager;
        this.musica = musica;
        this.isMuted = false;

        pauseCamera = new OrthographicCamera();
        setPauseViewport(new FitViewport(1280, 720, pauseCamera));
        pauseCamera.setToOrtho(false);

        background = new Texture("Screen/PauseBackground.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon_GB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);
        parameter.size = 50;
        selectedFont = generator.generateFont(parameter);
        generator.dispose();

        selectedY = 200;
        isOptionSelected = false;
    }

    public void render(SpriteBatch batch) {
        getPauseViewport().apply();
        pauseCamera.update();

        batch.setProjectionMatrix(pauseCamera.combined);

        if (background != null) {
            batch.draw(background, 0, 0, getPauseViewport().getWorldWidth(), getPauseViewport().getWorldHeight());
        }
        drawText(batch, "Regresar", 200, selectedY == 200);
        drawText(batch, "Guardar y salir", 150, selectedY == 150);
        drawText(batch, "Silenciar musica", 100, selectedY == 100);
        drawText(batch, "Salir", 50, selectedY == 50);
    }

    private void drawText(SpriteBatch batch, String text, float y, boolean isSelected) {
        BitmapFont currentFont = isSelected ? selectedFont : font;
        GlyphLayout layout = new GlyphLayout(currentFont, text);
        float x = (getPauseViewport().getWorldWidth() - layout.width) / 2;
        float adjustedY = (getPauseViewport().getWorldHeight() / 2) + y - 150;

        currentFont.draw(batch, layout, x, adjustedY);
    }

    public void dispose() {
        font.dispose();
        selectedFont.dispose();
        if (background != null) {
            background.dispose();
        }
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            if (selectedY == 200) {
                isOptionSelected = true;
            } else if (selectedY == 150) {
                saveGame();
                Gdx.app.exit();
            } else if (selectedY == 100) {
            	 toggleMute();
            } else if (selectedY == 50) {
                Gdx.app.exit();
            }
        }
        if ((Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.S) || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.DOWN)) && selectedY > 50) {
            selectedY -= 50;
        }
        if ((Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.W) || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.UP)) && selectedY < 200) {
            selectedY += 50;
        }
    }

    private void toggleMute() {
        isMuted = !isMuted;
        if (isMuted) {
            musica.stopMapMusic();
        } else {
            musica.playMapMusic();
        }
    }

    private void saveGame() {
        int currentScore = fightManager.getScore();
        String playerName = fightManager.getPlayerName();
        DataBase.saveScore(playerName, currentScore);
    }

    public boolean isOptionSelected() {
        return isOptionSelected;
    }

    public Viewport getPauseViewport() {
        return pauseViewport;
    }

    public void setPauseViewport(Viewport pauseViewport) {
        this.pauseViewport = pauseViewport;
    }

    public void resetOptionSelected() {
        isOptionSelected = false;
    }

    public boolean isMuted() {
        return isMuted;
    }
}
