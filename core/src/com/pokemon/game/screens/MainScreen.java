package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;
import com.pokemon.game.MyPokemonGame;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

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

    public MainScreen(MyPokemonGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        background = new Texture("BackgroundMenu.png");

        // Cargar y configurar la fuente personalizada
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon_GB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40; // Tamaño de la fuente
        font = generator.generateFont(parameter);
        parameter.size = 50; // Tamaño de la fuente seleccionada
        selectedFont = generator.generateFont(parameter);
        parameter.size = 20; // Tamaño de la fuente de instrucción
        instructionFont = generator.generateFont(parameter);
        generator.dispose(); // Liberar recursos del generador

        selectedY = 200; // Inicia con "Jugar" seleccionado

        startTime = TimeUtils.millis();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Keys.ENTER) {
                    if (selectedY == 200) {
                        isButtonPressed = true;
                    } else if (selectedY == 100) {
                        Gdx.app.exit();
                    }
                    return true;
                }
                if ((keycode == Keys.S || keycode == Keys.DOWN) && selectedY == 200) {
                    selectedY = 100;
                    return true;
                }
                if ((keycode == Keys.W || keycode == Keys.UP) && selectedY == 100) {
                    selectedY = 200;
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        if (isButtonPressed) {
            game.setScreen(new GameScreen(game));
            dispose();
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        // Dibuja el texto "Jugar" y "Salir"
        drawText("Jugar", 200, selectedY == 200);
        drawText("Salir", 100, selectedY == 100);
        
        // Calcula el tiempo transcurrido
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);

        // Cambia el tamaño del texto con una animación personalizada
        float scale = getCustomScale(elapsedTime);

        // Dibuja las instrucciones parpadeando
        drawInstructions("Usa W y S para desplazarte, ENTER para seleccionar", 550, scale);

        game.batch.end();
    }

    private float getCustomScale(long elapsedTime) {
        float cycleTime = 2000; // Tiempo de un ciclo completo en milisegundos
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
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;

        // Dibuja el texto
        currentFont.draw(game.batch, layout, x, y);
    }

    private void drawInstructions(String text, float y, float scale) {
        instructionFont.getData().setScale(scale);
        GlyphLayout layout = new GlyphLayout(instructionFont, text);
        float x = (Gdx.graphics.getWidth() - layout.width * scale) / 2;

        // Dibuja el texto de las instrucciones
        instructionFont.draw(game.batch, layout, x, y);
        instructionFont.getData().setScale(1); // Restaura la escala original
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        selectedFont.dispose();
        instructionFont.dispose();
    }
}
