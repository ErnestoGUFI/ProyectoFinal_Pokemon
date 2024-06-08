package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.TimeUtils;
import com.pokemon.game.MyPokemonGame;

import Audio.Musica;
import Audio.Sonido;
import DataBase.DataBase;
import Tutorial.TutorialScreen;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import java.util.List;

public class MainScreen extends ScreenAdapter {
    private MyPokemonGame game;
    private OrthographicCamera camera;
    private Texture background;
    private BitmapFont font;
    private BitmapFont selectedFont;
    private BitmapFont instructionFont;
    private boolean isButtonPressed = false;
    private float selectedY;
    private long startTime;
    private Musica musicaMenu;
    private Sonido sonidoPresionado;
    private Sonido cambiarOpcion;

    public MainScreen(MyPokemonGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        background = new Texture("Screen/BackgroundMenu.png");

        musicaMenu = new Musica();
        musicaMenu.playMenuMusic();
        
        sonidoPresionado = new Sonido();
        cambiarOpcion = new Sonido();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon_GB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);
        parameter.size = 50;
        selectedFont = generator.generateFont(parameter);
        parameter.size = 20;
        instructionFont = generator.generateFont(parameter);
        generator.dispose();

        selectedY = 200;

        startTime = TimeUtils.millis();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Keys.ENTER) {
                    if (selectedY == 200) {
                        isButtonPressed = true;
                        sonidoPresionado.playPressedSound();
                        musicaMenu.stopMenuMusic();
                        game.setScreen(new TutorialScreen(game));
                    } else if (selectedY == 150) {
                        game.setScreen(new ScoreScreen(game));
                        musicaMenu.stopMenuMusic();
                    } else if (selectedY == 100) {
                        Gdx.app.exit();
                    }
                    return true;
                }
                if ((keycode == Keys.S || keycode == Keys.DOWN) && selectedY > 100) {
                    selectedY -= 50;
                    cambiarOpcion.playCambiarOpcion();
                    return true;
                }
                if ((keycode == Keys.W || keycode == Keys.UP) && selectedY < 200) {
                    selectedY += 50;
                    cambiarOpcion.playCambiarOpcion();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        if (isButtonPressed) {
            game.setScreen(new GameScreen(game, "Jugador Default"));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        drawText("Jugar", 200, selectedY == 200);
        drawText("Puntaje", 150, selectedY == 150);
        drawText("Salir", 100, selectedY == 100);

        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        float scale = getCustomScale(elapsedTime);
        drawInstructions("Usa W y S para desplazarte, ENTER para seleccionar", 50, scale);

        game.batch.end();
    }

    private float getCustomScale(long elapsedTime) {
        float cycleTime = 2000;
        float halfCycle = cycleTime / 2;
        float time = elapsedTime % cycleTime;

        if (time < halfCycle) {
            return Interpolation.sine.apply(0.95f, 1.05f, time / halfCycle);
        } else {
            return Interpolation.sine.apply(1.05f, 0.95f, (time - halfCycle) / halfCycle);
        }
    }

    private void drawText(String text, float y, boolean isSelected) {
        BitmapFont currentFont = isSelected ? selectedFont : font;
        GlyphLayout layout = new GlyphLayout(currentFont, text);
        currentFont.draw(game.batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, y);
    }

    private void drawInstructions(String text, float y, float scale) {
        instructionFont.getData().setScale(scale);
        GlyphLayout layout = new GlyphLayout(instructionFont, text);
        instructionFont.draw(game.batch, layout, (Gdx.graphics.getWidth() - layout.width) / 2, y);
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        selectedFont.dispose();
        instructionFont.dispose();
        musicaMenu.dispose();
        sonidoPresionado.dispose();
        cambiarOpcion.dispose();
    }
}
