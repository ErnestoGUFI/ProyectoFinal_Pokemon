package com.pokemon.game.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

import Pokemons.Pokemon;


public class FightScreen implements Screen {

    MyPokemonGame game;

    // Textures for the initial scene and battle scene
    private Texture entrenadorSprite, fondoInicioBatalla, pokemonEnemigoSprite, vsSprite;
    private Texture fondoBatalla, pokemonAmigoSprite;

    // Animation coordinates
    private int ySpriteJugador = 0;
    private int ySpriteEnemigo = 369;

    // Timer for game time tracking
    Timer tiempo;
    private int seg = 0;

    // ShapeRenderer and BitmapFont for rendering shapes and text
    private ShapeRenderer sr;
    private BitmapFont text, text1;

    // Battle variables
    private float porcentajeJugador = 1f;
    private float porcentajeEnemigo = 1f;
    private boolean turno = true;

    private OrthographicCamera cameraFight;
    private Viewport viewportFight;

    private Pokemon pokemonAmigo, pokemonEnemigo;

    private String narracion;

    private boolean paused = false;
    private float pauseTimer = 0f;
    private final float PAUSE_DURATION = 2f;

    private int selectedAttackIndex = 0; // Variable para llevar el seguimiento del ataque seleccionado

    public FightScreen(MyPokemonGame game) {
        this.game = game;
        this.game.batch = new SpriteBatch();

        cameraFight = new OrthographicCamera();
        viewportFight = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cameraFight);
        viewportFight.apply();

        entrenadorSprite = new Texture("entrenadorpelea.png");
        fondoInicioBatalla = new Texture("fondoInicioBatalla.png");
        pokemonEnemigoSprite = new Texture("squirtleSprite.png");
        vsSprite = new Texture("vsSprite.png");
        fondoBatalla = new Texture("fondoBatalla.png");
        pokemonAmigoSprite = new Texture("bulbasaurSprite.png");

        tiempo = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seg++;
            }
        });

        sr = new ShapeRenderer();
        initFonts();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon_GB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        text = generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon_GB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter1.size = 16;
        text1 = generator1.generateFont(parameter1);
        generator1.dispose();
    }

    @Override
    public void show() {
        pokemonAmigo = new Pokemon("Bulbasaur", 100, pokemonAmigoSprite, "Placaje", 15, "Latigo Cepa", 15, "Somnifero",
                20, "Intimidacion", 21);
        pokemonEnemigo = new Pokemon("Squirtle", 100, pokemonEnemigoSprite, "Placaje", 15, "Chorro de agua", 15,
                "Escudo", 20, "Intimidacion", 21);
        
        narracion = "¿Que va a hacer " + pokemonAmigo.nombre + "?";
        
        tiempo.start();
    }

    @Override
    public void render(float delta) {
        if (paused) {
            pauseTimer += delta;
            if (pauseTimer >= PAUSE_DURATION) {
                pauseTimer = 0f;
                paused = false;
            }
            return;
        }

        if (seg < 5) {
        	introBatalla();
        } else if (seg >= 5) {
            if (turno) {
            	batallaScreen();
                seleccionAtaque();
            } else {
            	paused = true;
            	turnoEnemigo();
            	turno = true;
            }
        }
        
        if(pokemonEnemigo.vida<=0){
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        	game.setScreen(new GameScreen(game));
        }
    }

    private void seleccionAtaque() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedAttackIndex = (selectedAttackIndex + 1) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedAttackIndex = (selectedAttackIndex - 1 + 4) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            String selectedAttackName = pokemonAmigo.atacks[selectedAttackIndex].nombre;
            System.out.println("Ataque seleccionado: " + selectedAttackName);   
            if(pokemonEnemigo.vida > 0) {
            	narracion = (pokemonAmigo.nombre + " ha usado " + selectedAttackName);
                porcentajeEnemigo = (float)(pokemonAmigo.atacks[selectedAttackIndex].atacar(pokemonEnemigo));
                turno = true;
                
            }
        }
    }

    private void introBatalla() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(fondoInicioBatalla, 0, 0, 1280, 720);
        game.batch.draw(entrenadorSprite, 50, ySpriteJugador, 400, 400);
        game.batch.draw(pokemonEnemigo.pokemonSprite, 770, ySpriteEnemigo, 500, 400);
        game.batch.draw(vsSprite, 580, 300, 150, 170);

        if (ySpriteJugador < 69)
            ySpriteJugador++;
        if (ySpriteEnemigo > 300)
            ySpriteEnemigo--;

        game.batch.end();
    }

    private void batallaScreen() {
        Gdx.gl.glClearColor(240 / 255f, 240 / 255f, 240 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(fondoBatalla, 48, 47, 1178, 630);
        game.batch.end();

        renderShapes();

        game.batch.begin();
        dibujarElementos();
        game.batch.end();
    }
    
    private void renderShapes() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(205 / 255f, 205 / 255f, 205 / 255f, 0.8f));
        sr.rect(48, 47, 1178, 150);
        roundRect(sr, 90, 63, 520, 120, 20, new Color(175 / 255f, 175 / 255f, 175 / 255f, 0.8f));

        roundRect(sr, 695, 130, 210, 52, 10, new Color(255 / 255f, 0 / 255f, 0 / 255f, 0.8f));
        roundRect(sr, 965, 130, 210, 52, 10, new Color(255 / 255f, 0 / 255f, 0 / 255f, 0.8f));
        roundRect(sr, 695, 58, 210, 52, 10, new Color(255 / 255f, 0 / 255f, 0 / 255f, 0.8f));
        roundRect(sr, 965, 58, 210, 52, 10, new Color(255 / 255f, 0 / 255f, 0 / 255f, 0.8f));

        //roundRect(sr, 698, 135, 204, 42, 6, new Color(248 / 255f, 168 / 255f, 176 / 255f, 0.8f));

        sr.setColor(new Color(240 / 255f, 240 / 255f, 240 / 255f, 1));
        sr.rect(60, 565, 400, 100);
        sr.rect(810, 210, 400, 100);

        sr.setColor(Color.GREEN);
        
        sr.rect(905, 230, 280 * porcentajeJugador, 10);
        sr.rect(155, 585, 280 * porcentajeEnemigo, 10);

        sr.end();
    }
    
    private void dibujarElementos() {
        // Dibuja Pokémon
        game.batch.draw(pokemonAmigo.pokemonSprite, 250, 160, 300, 300);
        game.batch.draw(pokemonEnemigo.pokemonSprite, 750, 360, 270, 220);

        // Dibuja texto de narración
        text.setColor(new Color(0, 0, 0, 0.8f));
        text.draw(game.batch, narracion, 120f, 135f);

        // Guardar el color actual para restablecerlo después
        Color originalColor = text1.getColor().cpy();

        // Dibuja nombres de ataques
        for (int i = 0; i < 4; i++) {
            float attackTextX = 720f;
            float attackTextY = 160f - i * 70;
            if (i >= 2) {
                attackTextX = 980f;
                attackTextY = 160f - (i - 2) * 70;
            }
            if (i == selectedAttackIndex) {
                text1.setColor(Color.YELLOW); // Cambia el color del texto del ataque seleccionado
            } else {
                text1.setColor(originalColor); // Restaura el color original
            }
            text1.draw(game.batch, pokemonAmigo.atacks[i].nombre, attackTextX, attackTextY); // Ajusta la posición en función del índice
        }

        // Restaurar el color original
        text1.setColor(originalColor);

        // Dibuja nombres de Pokémon
        text1.setColor(new Color(0,0,0,0.8f));
        text1.draw(game.batch, pokemonEnemigo.nombre, 80, 650);
        text1.draw(game.batch, pokemonAmigo.nombre, 830, 295);

        // Dibuja barras de salud
        text1.draw(game.batch, "Vida", 830f, 245f); 
        text1.draw(game.batch, "Vida", 80f, 600f);
    }


    private void turnoEnemigo() {
        Random random = new Random();
        int attack = random.nextInt(4);
        narracion = pokemonEnemigo.nombre + " ha usado " + pokemonEnemigo.atacks[attack].nombre;
        paused = true;
    }

    @Override
    public void resize(int width, int height) {
        viewportFight.update(width, height);
    }

    @Override
    public void pause() {
        tiempo.stop();
    }

    @Override
    public void resume() {
        tiempo.start();
    }

    @Override
    public void hide() {
        tiempo.stop();
        dispose();
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        sr.dispose();
        entrenadorSprite.dispose();
        fondoInicioBatalla.dispose();
        pokemonEnemigoSprite.dispose();
        vsSprite.dispose();
        fondoBatalla.dispose();
        pokemonAmigoSprite.dispose();
        text.dispose();
        text1.dispose();
    }
    
    private void roundRect(ShapeRenderer renderer, float x, float y, float width, float height, float radius, Color color) {
        renderer.setColor(color);
        renderer.rect(x + radius, y, width - 2 * radius, height);
        renderer.rect(x, y + radius, width, height - 2 * radius);
        renderer.circle(x + radius, y + radius, radius);
        renderer.circle(x + width - radius, y + radius, radius);
        renderer.circle(x + radius, y + height - radius, radius);
        renderer.circle(x + width - radius, y + height - radius, radius);
    }
}