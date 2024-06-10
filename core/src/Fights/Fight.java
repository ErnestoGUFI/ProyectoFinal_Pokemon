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
import Audio.SonidoPokemon;
import Pokemons.Pokemon;

public class Fight {
	
	private MyPokemonGame game;
	private Sonido sonido; 
	private SonidoPokemon sonidoPokemon; 

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
    
    private enum Estado {
        MENU_OPCIONES,
        SELECCION_ATAQUE,
        EJECUTAR_ATAQUE,
        ESPERAR,
        CAMBIAR_POKEMON
    }

    private Estado estadoActual = Estado.MENU_OPCIONES;

    private String[] opcionesMenu = {"Atacar", "Curar", "Cambiar Pokémon", "Huir"};
    private int selectedOptionIndex = 0;
    
    private boolean paused = false;
    private float pauseTimer = 0f;
    private final float PAUSE_DURATION = 2f;

    private int selectedAttackIndex = 0;
    private int contadorPociones=3;
    private int selectedChangeOption = 0;
    
    private int pokemonInicialIndex=0;
    
    private String pokemonNames[] = {null,null,null,null};
    
    private Pokemon listaPokemon[] = {null,null,null,null,null,};
    
    public Fight(MyPokemonGame game, Pokemon[] listaPokemon) {
        this.game = game;
        
        //sonidos
        sonido = new Sonido(); 
        sonidoPokemon = new SonidoPokemon();
        
        this.listaPokemon = listaPokemon;
        
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
        pokemonAmigo = listaPokemon[0];
        pokemonEnemigo = new Pokemon("Squirtle", 100, pokemonEnemigoSprite, "Placaje", 15, "Chorro de agua", 15, "Escudo", 20, "Intimidacion", 21);
        
        narracion = "¿Qué va a hacer " + pokemonAmigo.nombre + "?";
        
        cameraFight = new OrthographicCamera();
        viewportFight = new FitViewport(1280, 720, cameraFight);
        cameraFight.setToOrtho(false);
        viewportFight.apply();
    }

    //seleccionas que deseas hacer
    public boolean seleccion() {
    	if(pokemonAmigo.vida<=0) {
    		pokemonAmigo.vida=0;
        	estadoActual=Estado.CAMBIAR_POKEMON;
        	return procesarMenuCambio();
    	}else if (estadoActual == Estado.MENU_OPCIONES) {
            return procesarMenuOpciones(); 
        } else if (estadoActual == Estado.SELECCION_ATAQUE) {
            return procesarSeleccionAtaque();
        } else if(estadoActual == Estado.CAMBIAR_POKEMON) {
        	return procesarMenuCambio();
        }
        	
        return false;
    }
    
    //detectar que opcion seleccionaste
    private boolean procesarMenuCambio() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            sonido.playCambiarOpcion();
            selectedChangeOption = (selectedChangeOption + 1) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            sonido.playCambiarOpcion();
            selectedChangeOption = (selectedChangeOption - 1 + 4) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            return ejecutarAccionCambio();
        }
        return false;
    }
    
    private boolean ejecutarAccionCambio() {
        Pokemon nuevoPokemonAmigo = null;
        
        switch (selectedChangeOption) {
            case 0:
                sonido.playPressedSound();
                for (Pokemon poke : listaPokemon) {
                    if (poke.nombre.equals(pokemonNames[0])) {
                        nuevoPokemonAmigo = poke;
                        
                        break;
                    }
                }
                break;
            case 1:
                sonido.playPressedSound();
                for (Pokemon poke : listaPokemon) {
                    if (poke.nombre.equals(pokemonNames[1])) {
                        nuevoPokemonAmigo = poke;
                        break;
                    }
                }
                break;
            case 2:
                sonido.playPressedSound();
                for (Pokemon poke : listaPokemon) {
                    if (poke.nombre.equals(pokemonNames[2])) {
                        nuevoPokemonAmigo = poke;
                        break;
                    }
                }
                break;
            case 3:
                sonido.playPressedSound();
                for (Pokemon poke : listaPokemon) {
                    if (poke.nombre.equals(pokemonNames[3])) {
                        nuevoPokemonAmigo = poke;
                        break;
                    }
                }
                break;
        }

        if (nuevoPokemonAmigo != null) {
            pokemonAmigo = nuevoPokemonAmigo;
            sonidoPokemon(pokemonAmigo.nombre);
            porcentajeJugador = (float) pokemonAmigo.vida / 100; 
        }

        estadoActual = Estado.MENU_OPCIONES;
        return false;
    }
    
    private void sonidoPokemon(String nombrePokemon) {
    	if (nombrePokemon.equals("Pikachu")){
    		sonidoPokemon.playPikachu();
    	}
    	if (nombrePokemon.equals("Bulbasaur")){
    		sonidoPokemon.playBulbasur();
    	}
    	if (nombrePokemon.equals("Charizard")){
    		sonidoPokemon.playCharizard();
    	}
    	if (nombrePokemon.equals("Pidgeot")){
    		sonidoPokemon.playPidgeot();
    	}
    	if (nombrePokemon.equals("Squirtle")){
    		sonidoPokemon.playSquirtle();
    	}
    }

    
    //detectar que opcion seleccionaste
    private boolean procesarMenuOpciones() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            sonido.playCambiarOpcion();
            selectedOptionIndex = (selectedOptionIndex + 1) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            sonido.playCambiarOpcion();
            selectedOptionIndex = (selectedOptionIndex - 1 + 4) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            return ejecutarAccionMenu();
        }
        return false;
    }

    //ejecutar la opcion seleccionada
    private boolean ejecutarAccionMenu() {
        switch (selectedOptionIndex) {
            case 0:
                estadoActual = Estado.SELECCION_ATAQUE;
                sonido.playPressedSound();                        
                break;
            case 1:
                return usarPocion();
            case 2:
            	estadoActual = Estado.CAMBIAR_POKEMON;
                narracion = pokemonAmigo.nombre + " cambia de Pokémon.";  
                break;
            case 3:                       
                narracion = "¡Has huido de la batalla!";
                pokemonEnemigo.vida=0;
                break;
        }
        return false;
    }

    //curarte
    private boolean usarPocion() {
        if (contadorPociones > 0) {
            sonido.playHealth();
            pokemonAmigo.vida += 25;
            if (pokemonAmigo.vida > 100) {
                pokemonAmigo.vida = 100; 
            }
            porcentajeJugador = (float) pokemonAmigo.vida / 100;
            contadorPociones--;
            narracion = pokemonAmigo.nombre + " se ha curado. " + contadorPociones + "/3";
            return true;
        } else {
            narracion = "Ya no tienes curaciones.";
            sonido.playError();
            return false;
        }
    }

    //seleccionar ataque
    private boolean procesarSeleccionAtaque() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
        	sonido.playCambiarOpcion();
            selectedAttackIndex = (selectedAttackIndex + 1) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
        	sonido.playCambiarOpcion();
            selectedAttackIndex = (selectedAttackIndex - 1 + 4) % 4;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            return ejecutarAtaque();
        }
        return false;
    }

    //usar ataque
    private boolean ejecutarAtaque() {
        sonido.playAtaqueSonido();
        estadoActual = Estado.EJECUTAR_ATAQUE;
        String selectedAttackName = pokemonAmigo.atacks[selectedAttackIndex].nombre;
        narracion = (pokemonAmigo.nombre + " ha usado " + selectedAttackName);
        porcentajeEnemigo = (float)(pokemonAmigo.atacks[selectedAttackIndex].atacar(pokemonEnemigo));
        estadoActual = Estado.MENU_OPCIONES;
        return true;
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
    
  
    public void renderShapes() {
        sr.begin(ShapeRenderer.ShapeType.Filled);

        // Color de fondo de las áreas de texto y menú
        sr.setColor(new Color(205 / 255f, 205 / 255f, 205 / 255f, 0.8f));
        sr.rect(48, 47, 1178, 150);
        roundRect(sr, 90, 63, 520, 120, 20, new Color(175 / 255f, 175 / 255f, 175 / 255f, 0.8f));

        if (estadoActual == Estado.MENU_OPCIONES) {
            Color[] buttonColors = {Color.RED, Color.YELLOW, Color.GREEN, Color.ORANGE};
            for (int i = 0; i < 4; i++) {
                roundRect(sr, 695 + 270 * (i % 2), 130 - 72 * (i / 2), 210, 52, 10, buttonColors[i]);
            }
        } else if (estadoActual == Estado.SELECCION_ATAQUE) {       
            for (int i = 0; i < 4; i++) {
                roundRect(sr, 695 + 270 * (i % 2), 130 - 72 * (i / 2), 210, 52, 10, Color.RED);
            }
        } else if(estadoActual == Estado.CAMBIAR_POKEMON) {
            for (int i = 0; i < 4; i++) {
                roundRect(sr, 695 + 270 * (i % 2), 130 - 72 * (i / 2), 210, 52, 10, Color.BLUE);
            }
        }

        // Fondo blanco para las barras de vida
        sr.setColor(new Color(240 / 255f, 240 / 255f, 240 / 255f, 1));
        sr.rect(60, 565, 400, 100);
        sr.rect(810, 210, 400, 100);

        // Determinar el color de la barra de vida del jugador
        Color colorVidaJugador = determinarColorVida(porcentajeJugador);
        sr.setColor(colorVidaJugador);
        sr.rect(905, 230, 280 * porcentajeJugador, 10);

        // Determinar el color de la barra de vida del enemigo
        Color colorVidaEnemigo = determinarColorVida(porcentajeEnemigo);
        sr.setColor(colorVidaEnemigo);
        sr.rect(155, 585, 280 * porcentajeEnemigo, 10);

        sr.end();
    }

    
    private Color determinarColorVida(float porcentajeVida) {
        if (porcentajeVida <= 0.3f) {
            return Color.RED;
        } else if (porcentajeVida <= 0.6f) {
            return Color.ORANGE;
        } else {
            return Color.GREEN;
        }
    }
  
    // Método para dibujar elementos
    public void dibujarElementos() {
        // Dibuja Pokémon
        game.batch.draw(pokemonAmigo.pokemonSprite, 490, 210, 150 * (-1), 150);
        game.batch.draw(pokemonEnemigo.pokemonSprite, 785, 380, 150, 150);

        // Dibuja texto de narración
        text.setColor(new Color(0, 0, 0, 0.8f));
        text.draw(game.batch, narracion, 100f, 135f);

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
        } else if (estadoActual == Estado.CAMBIAR_POKEMON) {
            int j = 0;
            int k = 0;
            for (int i = 0; i < 4; i++) {
                float attackTextX = 720f;
                float attackTextY = 160f - i * 70;
                if (i >= 2) {
                    attackTextX = 980f;
                    attackTextY = 160f - (i - 2) * 70;
                }
                if (i == selectedChangeOption) {
                    text1.setColor(Color.YELLOW); // Cambia el color del texto del pokemon seleccionado.
                } else {
                    text1.setColor(originalColor); // Restaura el color original
                }

                if (listaPokemon[j].nombre.equals(pokemonAmigo.nombre)) {
                    text1.draw(game.batch, listaPokemon[j + 1].nombre, attackTextX, attackTextY); // Ajusta la posición en función del índice
                    pokemonNames[k] = listaPokemon[j + 1].nombre;
                    j += 2;
                    k++;
                } else {
                    text1.draw(game.batch, listaPokemon[j].nombre, attackTextX, attackTextY); // Ajusta la posición en función del índice
                    pokemonNames[k] = listaPokemon[j].nombre;
                    j++;
                    k++;
                }
            }
        }

        
        text1.setColor(originalColor);

        
        text1.setColor(new Color(0, 0, 0, 0.8f));
        text1.draw(game.batch, pokemonEnemigo.nombre, 80, 650);
        text1.draw(game.batch, pokemonAmigo.nombre, 830, 295);

        int vidaMaxima = 100;
        
        int vidaActualAmigo = pokemonAmigo.vida; 
        String vidaTextoAmigo = vidaActualAmigo + "/" + vidaMaxima;
        text1.draw(game.batch, vidaTextoAmigo, 1080f, 260f);
        
        int vidaActualEnemigo = pokemonEnemigo.vida; 
        String vidaTextoEnemigo = vidaActualEnemigo + "/" + vidaMaxima;
        text1.draw(game.batch, vidaTextoEnemigo, 330f, 615f);

        // Yo
        text1.draw(game.batch, "Vida", 830f, 245f);
        //enemigo
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

    public void roundRect(ShapeRenderer renderer, float x, float y, float width, float height, float radius, Color color) {
        renderer.setColor(color);
        renderer.rect(x + radius, y, width - 2 * radius, height);
        renderer.rect(x, y + radius, width, height - 2 * radius);
        renderer.circle(x + radius, y + radius, radius);
        renderer.circle(x + width - radius, y + radius, radius);
        renderer.circle(x + radius, y + height - radius, radius);
        renderer.circle(x + width - radius, y + height - radius, radius);
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
