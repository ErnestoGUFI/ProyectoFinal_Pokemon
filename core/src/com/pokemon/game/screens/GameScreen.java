package com.pokemon.game.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pokemon.game.MyPokemonGame;

import Audio.Musica;
import Maps.Mapa;
import Player.Controles;
import Player.Jugador;
import Pokemons.Pokemon;

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
    private boolean pelea = false;
    private Fight peleaScreen;
    private int seg = 0;
    private MyPokemonGame game;
    private boolean turno = true;
    
    private Pokemon[] arregloPokemon = {
    		new Pokemon("Bulbasaur",100,new Texture("bulbasaurSprite.png"),"Placaje",20,"Latigo cepa",15,"Arañazo",12,"Embestida",20),
    		new Pokemon("Ivysaur", 100, new Texture("ivysaurSprite.png"), "Hoja Afilada", 20, "Látigo Cepa", 15, "Polvo Veneno", 12, "Bomba Germen", 20),
    		new Pokemon("Venusaur", 100, new Texture("venusaurSprite.png"), "Hoja Afilada", 20, "Látigo Cepa", 15, "Polvo Veneno", 12, "Bomba Germen", 20),
    		new Pokemon("Charmander", 100, new Texture("charmanderSprite.png"), "Lanzallamas", 20, "Ascuas", 15, "Garra Dragón", 12, "Infierno", 20),
    		new Pokemon("Charmeleon", 100, new Texture("charmeleonSprite.png"), "Lanzallamas", 20, "Ascuas", 15, "Garra Dragón", 12, "Infierno", 20),
    		new Pokemon("Charizard", 100, new Texture("charizardSprite.png"), "Lanzallamas", 20, "Ascuas", 15, "Garra Dragón", 12, "Infierno", 20),
    		new Pokemon("Squirtle", 100, new Texture("squirtleSprite.png"), "Pistola Agua", 20, "Burbuja", 15, "Mordisco", 12, "Hidropulso", 20),
    		new Pokemon("Wartortle", 100, new Texture("wartottleSprite.png"), "Pistola Agua", 20, "Burbuja", 15, "Mordisco", 12, "Hidropulso", 20),
    		new Pokemon("Blastoise", 100, new Texture("blastoiseSprite.png"), "Pistola Agua", 20, "Burbuja", 15, "Mordisco", 12, "Hidropulso", 20),
    		new Pokemon("Pikachu", 100, new Texture("pikachuSprite.png"), "Impactrueno", 20, "Rayo", 15, "Chispazo", 12, "Voltio Cruel", 20),
    		new Pokemon("Pidgey", 100, new Texture("pidgeySprite.png"), "Ataque Ala", 20, "Tornado", 15, "Pájaro Osado", 12, "Vendaval", 20),
    		new Pokemon("Pidgeotto", 100, new Texture("pidgeottoSprite.png"), "Ataque Ala", 20, "Tornado", 15, "Pájaro Osado", 12, "Vendaval", 20),
    		new Pokemon("Pidgeot", 100, new Texture("pidgeotSprite.png"), "Ataque Ala", 20, "Tornado", 15, "Pájaro Osado", 12, "Vendaval", 20),
    		new Pokemon("Abra", 100, new Texture("abraSprite.png"), "Psicorrayo", 20, "Confusión", 15, "Teletransporte", 12, "Premonición", 20),
    		new Pokemon("Kadabra", 100, new Texture("kadabraSprite.png"), "Psicorrayo", 20, "Confusión", 15, "Teletransporte", 12, "Premonición", 20),
    		new Pokemon("Alakazam", 100, new Texture("alakazamSprite.png"), "Psicorrayo", 20, "Confusión", 15, "Teletransporte", 12, "Premonición", 20),
    		new Pokemon("Vaporeon", 100, new Texture("vaporeonSprite.png"), "Aqua Jet", 20, "Hidrobomba", 15, "Rayo Burbuja", 12, "Cola Surf", 20),
    		new Pokemon("Jolteon", 100, new Texture("jolteonSprite.png"), "Chispa", 20, "Trueno", 15, "Voltio Cruel", 12, "Bola Voltio", 20),
    		new Pokemon("Flareon", 100, new Texture("flareonSprite.png"), "Ascuas", 20, "Llama", 15, "Sofoco", 12, "Puño Fuego", 20),
    		new Pokemon("Nidoran hembra", 100, new Texture("nidoranhembraSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidorina", 100, new Texture("nidorinaSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidoqueen", 100, new Texture("nidoqueenSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Ratatta", 100, new Texture("ratattaSprite.png"), "Ataque Rápido", 20, "Mordisco", 15, "Hipercolmillo", 12, "Tajo Umbrío", 20),
    		new Pokemon("Raticate", 100, new Texture("raticateSprite.png"), "Ataque Rápido", 20, "Mordisco", 15, "Hipercolmillo", 12, "Tajo Umbrío", 20),
    		new Pokemon("Nidoran macho", 100, new Texture("nidoranmachoSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidorino", 100, new Texture("nidorinoSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20),
    		new Pokemon("Nidoking", 100, new Texture("nidokingSprite.png"), "Doble Patada", 20, "Picotazo Venenoso", 15, "Golpe Cabeza", 12, "Puya Nociva", 20)
    };
    
    Timer tiempo;
    
    private boolean paused = false;
    private float pauseTimer = 0f;
    private final float PAUSE_DURATION = 2f;
    
    int pokemonRandom = -1;
    
    private Pokemon[] listaPokemon = {
    		arregloPokemon[0],
    		arregloPokemon[5],
    		arregloPokemon[6],
    		arregloPokemon[9],
    		arregloPokemon[12],
    		arregloPokemon[13],
    };

    public GameScreen(MyPokemonGame game) {
        this.game = game;
        musicaMapa = new Musica();
        musicaMapa.playMapMusic();
        peleaScreen = new Fight(game);
        
        
        tiempo = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				seg++;
				System.out.println(seg);
				
			}
        	
        });
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(480, 220, camera);
        viewport.apply();

        jugador = new Jugador(game);
        controles = new Controles();
       

        mapas = new Mapa[] {
            new Mapa("mapa.tmx", camera),
            new Mapa("mapa2.tmx", camera),
            new Mapa("Casa.tmx", camera),
        };
        mapaActualIndex = 0;
        isPaused = false;
        pausaScreen = new Pausa(game);

        Gdx.input.setInputProcessor(controles);
        
    }

    @Override
    public void render(float delta) {
    	
        
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (isPaused==false) {
                pause();
                //musicaMapa.pauseMapMusic();
            } 
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isPaused && !pelea) {
            camera.position.set(jugador.x, jugador.y, 0);
            camera.update();

            mapas[mapaActualIndex].render();

            pelea = jugador.update(controles, mapas[mapaActualIndex].getTiledMap(), this);

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            jugador.dibujar(game.batch, controles);
            game.batch.end();
        } else {
        	if(pelea) {
        		if(!tiempo.isRunning() && pokemonRandom == -1) {
        			tiempo.start();
        			Random rand = new Random();
        			pokemonRandom = rand.nextInt(arregloPokemon.length);
        			
        			peleaScreen.setPokemonEnemigo(arregloPokemon[pokemonRandom]);
        		}
        		
        		
        		peleaScreen.cameraFight.update();
        		game.batch.setProjectionMatrix(peleaScreen.cameraFight.combined);
        		
        		if (paused) {
        			peleaScreen.batallaScreen();
                    pauseTimer += delta;
                    if (pauseTimer >= PAUSE_DURATION) {
                    	peleaScreen.turnoEnemigo();
                        pauseTimer = 0f;
                        paused = false;
                    }
                    return;
                }
                peleaScreen.cameraFight.update();
                game.batch.setProjectionMatrix(peleaScreen.cameraFight.combined);
                peleaScreen.sr.setProjectionMatrix(peleaScreen.cameraFight.combined);
                
                if (seg < 5) {
                	System.out.println("seg<5");
                	peleaScreen.introBatalla();
                } else if (seg >= 5) {
                	if(tiempo.isRunning()) tiempo.stop();
                    if (turno) {
                    	peleaScreen.batallaScreen();
                    	if(peleaScreen.seleccionAtaque()) {
                    		turno = false;
                    	}
                    } else {
                        paused = true;
                        turno = true;
                    }
                }
                
                if(peleaScreen.pokemonEnemigo.vida<=0) {
                	pelea=false;
                	seg = 0;
                	arregloPokemon[pokemonRandom].vida = 100;
                	pokemonRandom = -1;
                	peleaScreen.ySpriteJugador = 0;
                	peleaScreen.ySpriteEnemigo = 520;
                	peleaScreen.porcentajeEnemigo = 1f;
                	peleaScreen.porcentajeJugador = 1f;
                }
        	}
        	
        	else {
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
        pausaScreen.getPauseViewport().update(width, height); // Actualizar viewport de la pantalla de pausa
    }

    @Override
    public void pause() {
        isPaused = true;
      //Musica del mapa
    }

    @Override
    public void resume() {
        isPaused = false;
        pausaScreen.resetOptionSelected(); // Restablecer la opción seleccionada
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
