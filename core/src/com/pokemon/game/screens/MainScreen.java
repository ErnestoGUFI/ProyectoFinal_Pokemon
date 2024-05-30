package com.pokemon.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

public class MainScreen implements Screen {
    private MyPokemonGame game;
    private OrthographicCamera camera;
    private Viewport viewport;  
    private Texture background;  
    private float elapsedTime = 0;  

    public MainScreen(MyPokemonGame game) {
        this.game = game;
        camera = new OrthographicCamera();  
        viewport = new FitViewport(MyPokemonGame.width, MyPokemonGame.height, camera);  
        background = new Texture("BackgroundMenu.png"); 
    }

    @Override
    public void show() {
        // Posiciona la cámara en el centro de la vista
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Acumula el tiempo transcurrido
        elapsedTime += delta;
        
        // Si han pasado 3 segundos, cambiar a la pantalla del juego
        if (elapsedTime >= 3) {
            game.setScreen(new GameScreen(game));
            dispose();
            return;
        }

        // Limpia la pantalla con un color negro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Ajusta la proyección de la cámara al lote de sprites
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        // Dibuja la imagen de fondo ajustada al tamaño de la ventana
        game.batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    	// Actualiza el viewport cuando se cambia el tamaño de la ventana
        viewport.update(width, height);  
        // Reposiciona la cámara en el centro
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0); 
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        background.dispose();  // Libera los recursos de la textura de fondo
    }
}