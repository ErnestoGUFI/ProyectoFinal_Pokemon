package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;
import com.pokemon.game.MyPokemonGame;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class PauseScreen extends ScreenAdapter {
    private MyPokemonGame game;
    private OrthographicCamera camera;
    private BitmapFont font;
    private BitmapFont selectedFont;
    private Texture background;
    private float selectedY;
    private boolean isOptionSelected = false;
    private long startTime;
    private GameScreen gameScreen;

    public PauseScreen(MyPokemonGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        background = new Texture("PauseBackground.png"); 

        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon_GB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40; 
        font = generator.generateFont(parameter);
        parameter.size = 50; 
        selectedFont = generator.generateFont(parameter);
        generator.dispose(); 

        selectedY = 200; 

        startTime = TimeUtils.millis();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Keys.ENTER) {
                    if (selectedY == 200) {
                        isOptionSelected = true;
                        game.setScreen(gameScreen); 
                    } else if (selectedY == 150) {
                        saveGame();
                        Gdx.app.exit(); 
                    } else if (selectedY == 100) {
                        Gdx.app.exit(); 
                    }
                    return true;
                }
                if ((keycode == Keys.S || keycode == Keys.DOWN) && selectedY > 100) {
                    selectedY -= 50;
                    return true;
                }
                if ((keycode == Keys.W || keycode == Keys.UP) && selectedY < 200) {
                    selectedY += 50;
                    return true;
                }
                return false;
            }
        });
    }

    private void saveGame() {
        
    }

    @Override
    public void render(float delta) {
        if (isOptionSelected) {
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        if (background != null) {
            game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        drawText("Regresar", 200, selectedY == 200);
        drawText("Guardar y salir", 150, selectedY == 150);
        drawText("Salir", 100, selectedY == 100);
        game.batch.end();
    }

    private void drawText(String text, float y, boolean isSelected) {
        BitmapFont currentFont = isSelected ? selectedFont : font;
        GlyphLayout layout = new GlyphLayout(currentFont, text);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float adjustedY = (Gdx.graphics.getHeight() / 2) + y - 150;
        
        currentFont.draw(game.batch, layout, x, adjustedY);
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        font.dispose();
        selectedFont.dispose();
        if (background != null) {
            background.dispose();
        }
    }
}

