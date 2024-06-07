package Fights;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

import Audio.Sonido;
import Pokemons.Pokemon;

public class Fight {
	
	private MyPokemonGame game;
	private Sonido sonido; 

    // Texturas para la escena inicial y de batalla
    private Texture entrenadorSprite, fondoInicioBatalla, pokemonEnemigoSprite, vsSprite;
    private Texture fondoBatalla, pokemonAmigoSprite;

    // Coordenadas de animación
    public int ySpriteJugador = 0;
    public int ySpriteEnemigo = 520;

    // Temporizador para el seguimiento del tiempo de juego
    Timer tiempo;
    private int seg = 0;

    // ShapeRenderer y BitmapFont para renderizar formas y texto
    public ShapeRenderer sr;
    private BitmapFont text, text1;

    // Variables de batalla
    public float porcentajeJugador = 1f;
    public float porcentajeEnemigo = 1f;
    private boolean turno = true;

    public OrthographicCamera cameraFight;
    private Viewport viewportFight;

    private Pokemon pokemonAmigo;
    Pokemon pokemonEnemigo;

    private String narracion;
    
    // Enum para los estados de la batalla
    private enum Estado {
        MENU_OPCIONES,
        SELECCION_ATAQUE,
        EJECUTAR_ATAQUE,
        ESPERAR
    }

    private Estado estadoActual = Estado.MENU_OPCIONES;

    private String[] opcionesMenu = {"Atacar", "Curar", "Cambiar Pokémon", "Huir"};
    private int selectedOptionIndex = 0;
    
    private boolean paused = false;
    private float pauseTimer = 0f;
    private final float PAUSE_DURATION = 2f;

    private int selectedAttackIndex = 0; // Variable para llevar el seguimiento del ataque seleccionado
    
    private int contadorPociones=3;
    
    public Fight(MyPokemonGame game) {
        this.game = game;
        sonido = new Sonido(); 
        
        cameraFight = new OrthographicCamera();
        viewportFight = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cameraFight);
        viewportFight.apply();

        // Carga de texturas
        entrenadorSprite = new Texture("Screen/entrenadorpelea.png");
        fondoInicioBatalla = new Texture("Screen/fondoInicioBatalla.png");
        pokemonEnemigoSprite = new Texture("Pokemons/charmanderSprite.png");
        vsSprite = new Texture("Screen/vsSprite.png");
        fondoBatalla = new Texture("Screen/fondoBatalla.png");
        pokemonAmigoSprite = new Texture("Pokemons/bulbasaurSprite.png");

        tiempo = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seg++;
            }
        });
        
        sr = new ShapeRenderer();
        initFonts();
        
        tiempo.start();
        
        // Inicialización de pokémon
        pokemonAmigo = new Pokemon("Bulbasaur", 100, pokemonAmigoSprite, "Placaje", 15, "Latigo Cepa", 15, "Somnifero", 20, "Intimidacion", 21);
        pokemonEnemigo = new Pokemon("Squirtle", 100, pokemonEnemigoSprite, "Placaje", 15, "Chorro de agua", 15, "Escudo", 20, "Intimidacion", 21);
        
        narracion = "¿Qué va a hacer " + pokemonAmigo.nombre + "?";
        
        cameraFight = new OrthographicCamera();
        viewportFight = new FitViewport(1280, 720, cameraFight);
        cameraFight.setToOrtho(false);
        viewportFight.apply();
    }

    // Método para la selección de ataques del jugador
    public boolean seleccionAtaque() {
        if (estadoActual == Estado.MENU_OPCIONES) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                sonido.playCambiarOpcion();
                selectedOptionIndex = (selectedOptionIndex + 1) % 4;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                sonido.playCambiarOpcion();
                selectedOptionIndex = (selectedOptionIndex - 1 + 4) % 4;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                switch (selectedOptionIndex) {
                    case 0:
                        estadoActual = Estado.SELECCION_ATAQUE;
                        sonido.playPressedSound();                        
                        break;
                    case 1:
                    	
                    	if(contadorPociones>0) {
	                        sonido.playHealth();
	                        pokemonAmigo.vida += 25; 
	                        porcentajeJugador = (float) pokemonAmigo.vida / 100;
	                        contadorPociones--;
	                        narracion = pokemonAmigo.nombre + " se ha curado. " + contadorPociones +"/3";
                        return true; 
                    	}
                    	else {
                    		narracion = "Ya no tienes curaciones." ;
                    		sonido.playError();
                    	}
                    	break;
                    case 2:
                        narracion = pokemonAmigo.nombre + " cambia de Pokémon.";  
                        break;
                    case 3:                       
                        narracion = "¡Has huido de la batalla!";
                        pokemonEnemigo.vida=0;
                        break;
                }
                return false;
            }
        } else if (estadoActual == Estado.SELECCION_ATAQUE) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                selectedAttackIndex = (selectedAttackIndex + 1) % 4;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                selectedAttackIndex = (selectedAttackIndex - 1 + 4) % 4;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                sonido.playAtaqueSonido();
                estadoActual = Estado.EJECUTAR_ATAQUE;
                String selectedAttackName = pokemonAmigo.atacks[selectedAttackIndex].nombre;
                narracion = (pokemonAmigo.nombre + " ha usado " + selectedAttackName);
                porcentajeEnemigo = (float)(pokemonAmigo.atacks[selectedAttackIndex].atacar(pokemonEnemigo));
                estadoActual = Estado.MENU_OPCIONES;
                return true;
            }
        }
        return false;
    }

    // Método para la introducción de la batalla
    public void introBatalla() {
        game.batch.begin();
        game.batch.draw(fondoInicioBatalla, 0, 0, 1280, 720);
        game.batch.draw(entrenadorSprite, 50, ySpriteJugador, 400, 400);
        game.batch.draw(pokemonEnemigo.pokemonSprite, 930, ySpriteEnemigo, 210, 210);
        game.batch.draw(vsSprite, 580, 300, 150, 170);

        if (ySpriteJugador < 69)
            ySpriteJugador++;
        if (ySpriteEnemigo > 451)
            ySpriteEnemigo--;

        game.batch.end();
    }

    // Método para la pantalla de batalla
    public void batallaScreen() {
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
    
    // Método para renderizar las formas
 // Método para renderizar las formas
    public void renderShapes() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        
        // Dibuja el borde blanco seguido de un borde negro para el primer rectángulo redondeado
        roundRect(sr, 48, 47, 1178, 150, 20, Color.WHITE);
        roundRect(sr, 48, 47, 1178, 150, 20, Color.BLACK);

        // Dibuja el borde blanco seguido de un borde negro para el segundo rectángulo redondeado
        roundRect(sr, 90, 63, 520, 120, 20, Color.WHITE);
        roundRect(sr, 90, 63, 520, 120, 20, Color.BLACK);

        if (estadoActual == Estado.MENU_OPCIONES) {
            Color[] buttonColors = {Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE};
            for (int i = 0; i < 4; i++) {
                roundRect(sr, 695 + 270 * (i % 2), 130 - 72 * (i / 2), 210, 52, 10, buttonColors[i]);
            }
        } else if (estadoActual == Estado.SELECCION_ATAQUE) {       
            for (int i = 0; i < 4; i++) {
                roundRect(sr, 695 + 270 * (i % 2), 130 - 72 * (i / 2), 210, 52, 10, Color.RED);
            }
        }

        sr.setColor(new Color(240 / 255f, 240 / 255f, 240 / 255f, 1));
        sr.rect(60, 565, 400, 100);
        sr.rect(810, 210, 400, 100);

        sr.setColor(Color.GREEN);

        sr.rect(905, 230, 280 * porcentajeJugador, 10);
        sr.rect(155, 585, 280 * porcentajeEnemigo, 10);

        sr.end();
    }


    // Método para dibujar elementos
    public void dibujarElementos() {
        // Dibuja Pokémon
        game.batch.draw(pokemonAmigo.pokemonSprite, 490, 210, 150 * (-1), 150);
        game.batch.draw(pokemonEnemigo.pokemonSprite, 785, 380, 150, 150);

        // Dibuja texto de narración
        text.setColor(new Color(0, 0, 0, 0.8f));
        text.draw(game.batch, narracion, 120f, 135f);

        // Guarda el color original para restablecerlo después
        Color originalColor = text1.getColor().cpy();

        if (estadoActual == Estado.MENU_OPCIONES) {
            // Dibuja opciones de menú
            for (int i = 0; i < 4; i++) {
                float optionTextX = 720f;
                float optionTextY = 160f - i * 70;
                if (i >= 2) {
                    optionTextX = 980f;
                    optionTextY = 160f - (i - 2) * 70;
                }
                if (i == selectedOptionIndex) {
                    text1.setColor(Color.YELLOW); // Cambia el color del texto de la opción seleccionada
                } else {
                    text1.setColor(originalColor); // Restaura el color original
                }
                text1.draw(game.batch, opcionesMenu[i], optionTextX, optionTextY);
            }
        } else if (estadoActual == Estado.SELECCION_ATAQUE) {
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
        }

        // Restaura el color original
        text1.setColor(originalColor);

        // Dibuja nombres de Pokémon
        text1.setColor(new Color(0, 0, 0, 0.8f));
        text1.draw(game.batch, pokemonEnemigo.nombre, 80, 650);
        text1.draw(game.batch, pokemonAmigo.nombre, 830, 295);

        // Dibuja barras de salud
        text1.draw(game.batch, "Vida", 830f, 245f);
        text1.draw(game.batch, "Vida", 80f, 600f);
    }

    // Método para el turno del enemigo
    public void turnoEnemigo() {
        Random random = new Random();
        int attack = random.nextInt(4);

        narracion = pokemonEnemigo.nombre + " ha usado " + pokemonEnemigo.atacks[attack].nombre;
        paused = true;

        porcentajeJugador = (float) (pokemonEnemigo.atacks[attack].atacar(pokemonAmigo));
    }

    public void resize(int width, int height) {
        viewportFight.update(width, height);
        cameraFight.position.set(cameraFight.viewportWidth / 2, cameraFight.viewportHeight / 2, 0);
        cameraFight.update();
    }

    public void pause() {
        tiempo.stop();
    }

    public void resume() {
        tiempo.start();
    }

    public void hide() {
        tiempo.stop();
        dispose();
    }

    // Método para liberar recursos
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

    // Método para dibujar un rectángulo redondeado
 // Método para dibujar un rectángulo redondeado con borde blanco seguido de borde negro
    public void roundRect(ShapeRenderer renderer, float x, float y, float width, float height, float radius, Color borderColor) {
        // Dibuja el borde blanco
        renderer.setColor(Color.WHITE);
        renderer.rect(x + radius, y, width - 2 * radius, height);
        renderer.rect(x, y + radius, width, height - 2 * radius);
        renderer.circle(x + radius, y + radius, radius);
        renderer.circle(x + width - radius, y + radius, radius);
        renderer.circle(x + radius, y + height - radius, radius);
        renderer.circle(x + width - radius, y + height - radius, radius);
        
        // Dibuja el borde negro exterior al borde blanco
        renderer.setColor(borderColor);
        renderer.rect(x + radius - 1, y - 1, width - 2 * radius + 2, height + 2);
        renderer.rect(x - 1, y + radius - 1, width + 2, height - 2 * radius + 2);
        renderer.circle(x + radius - 1, y + radius - 1, radius);
        renderer.circle(x + width - radius + 1, y + radius - 1, radius);
        renderer.circle(x + radius - 1, y + height - radius + 1, radius);
        renderer.circle(x + width - radius + 1, y + height - radius + 1, radius);
    }

	
    // Método para inicializar las fuentes
    public void initFonts() {
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
    
    // Método para establecer el Pokémon enemigo
    public void setPokemonEnemigo(Pokemon pokemonEnemigo) {
        this.pokemonEnemigo = pokemonEnemigo;
        this.pokemonEnemigoSprite = pokemonEnemigo.pokemonSprite;
    }
}
