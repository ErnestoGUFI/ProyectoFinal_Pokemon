package Player;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.pokemon.game.MyPokemonGame;
import com.pokemon.game.screens.FightScreen;
import com.pokemon.game.screens.GameScreen;

import Maps.Colisiones;
import Maps.Teleport;

public class Jugador {

    public float x;
    public float y;
    private float velocidad;
    private TexturaManager texturaManager;
    private int frameCount;
    private int maxFrameCount = 10;
    
    
    public MyPokemonGame pokemonGame;
    
    public Jugador(MyPokemonGame pokemonGame) {
        reiniciar();
        texturaManager = new TexturaManager();
        this.pokemonGame = pokemonGame;
    }

    public void reiniciar() {
        x = 950;
        y = 50;
        velocidad = 2;
    }

    public void update(Controles controles, TiledMap tiledMap, GameScreen game) {
        //Estas son las anteriores posiciones del jugador en x,y.
        float oldX = x;
        float oldY = y;
        
        Random random = new Random();
        int randomNumber = random.nextInt(200);
        
        frameCount--;

        if (controles.arriba) {
            y += velocidad;
            if (frameCount <= 0) {
                texturaManager.actualizarIndiceArriba();
                frameCount = maxFrameCount;
            }
        } else if (controles.abajo) {
            y -= velocidad;
            if (frameCount <= 0) {
                texturaManager.actualizarIndiceAbajo();
                frameCount = maxFrameCount;
            }
        } else if (controles.izquierda) {
            x -= velocidad;
            if (frameCount <= 0) {
                texturaManager.actualizarIndiceIzquierda();
                frameCount = maxFrameCount;
            }
        } else if (controles.derecha) {
            x += velocidad;
            if (frameCount <= 0) {
                texturaManager.actualizarIndiceDerecha();
                frameCount = maxFrameCount;
            }
        }

        if (controles.correr) {
            velocidad = 3;
        } else {
            velocidad = 2;
        }

        //Se crea un arreglo vertices que contiene los valores del poligono que representa el jugador.
        float[] vertices = new float[]{x + 3, y + 3, x + 7, y + 3, x + 7, y + 7, x + 3, y + 7}; 
        
        if (Colisiones.colisionConObjetos(tiledMap, vertices)) {
            x = oldX;
            y = oldY;
        }
        
        if (Teleport.TeleportMapa1(tiledMap, vertices)) {
            cambiarMapa(game, 0, -3, 655); // Coordenadas para mapa1
        }
        
        if (Teleport.TeleportMapa2(tiledMap, vertices)) {
            cambiarMapa(game, 1, 1015, 655); // Coordenadas para mapa2
        }
        
        if (Teleport.TeleportTienda(tiledMap, vertices)) {
            cambiarMapa(game, 3, 300, 200); // Coordenadas para tienda
        }
        
        if (Teleport.TeleportCasa(tiledMap, vertices)) {
            cambiarMapa(game, 2, 207, 49); // Coordenadas para casa
        }
        
        if (Teleport.TeleportVolver(tiledMap, vertices)) {
            cambiarMapa(game, 0, 888, 98); // Coordenadas para casa
        }
        if (Teleport.TeleportArbusto(tiledMap, vertices) && randomNumber == 7) {
         
        	System.out.println("Pelea Encontrada");
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        	pokemonGame.setScreen(new FightScreen(new MyPokemonGame()));
        }

        
    }
    
    public void dibujar(SpriteBatch batch, Controles controles) {
        Texture jugadorTexture = texturaManager.obtenerTextura(controles);
        batch.draw(jugadorTexture, x, y, 16, 16);
    }
    
    public void cambiarMapa(GameScreen game, int nuevoIndice, float newX, float newY) {
        game.cambiarMapa(nuevoIndice, newX, newY);
    }

    public void dispose() {
        texturaManager.dispose();
    }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

