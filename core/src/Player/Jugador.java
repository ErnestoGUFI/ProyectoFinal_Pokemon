package Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.pokemon.game.MyPokemonGame;
<<<<<<< HEAD
import com.pokemon.game.screens.GameScreen;
=======
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555

import Maps.Colisiones;
import Maps.Teleport;

public class Jugador {

    public float x;
    public float y;
    private float velocidad;
    private TexturaManager texturaManager;
    private int frameCount;
    private int maxFrameCount = 10;
<<<<<<< HEAD
    
=======

>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
    public Jugador() {
        reiniciar();
        texturaManager = new TexturaManager();
    }

    public void reiniciar() {
        x = 950;
        y = 50;
        velocidad = 2;
<<<<<<< HEAD
    }

    public void update(Controles controles, TiledMap tiledMap, GameScreen game) {
        //Estas son las anteriores posiciones del jugador en x,y.
    	float oldX = x;
        float oldY = y;
        
        frameCount--;

        if (controles.arriba) {
=======

    }

    public void update(Controles controles, TiledMap tiledMap, MyPokemonGame game) {
        float oldX = x;
        float oldY = y;
        frameCount--;

        if (controles.arriba) {
            oldY = y;
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
            y += velocidad;
            if (frameCount <= 0) {
                texturaManager.actualizarIndiceArriba();
                frameCount = maxFrameCount;
            }
        } else if (controles.abajo) {
<<<<<<< HEAD
=======
            oldY = y;
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
            y -= velocidad;
            if (frameCount <= 0) {
                texturaManager.actualizarIndiceAbajo();
                frameCount = maxFrameCount;
            }
        } else if (controles.izquierda) {
<<<<<<< HEAD
=======
            oldX = x;
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
            x -= velocidad;
            if (frameCount <= 0) {
                texturaManager.actualizarIndiceIzquierda();
                frameCount = maxFrameCount;
            }
        } else if (controles.derecha) {
<<<<<<< HEAD
=======
            oldX = x;
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
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

<<<<<<< HEAD
        //Se crea un arreglo vertices que contiene los valores del poligono que representa el jugador.
        float[] vertices = new float[]{x + 3, y + 3, x + 7, y + 3, x + 7, y + 7, x + 3, y + 7}; 
        
=======
        // Comprobar colisiones con los objetos del mapa
        float[] vertices = new float[]{x + 3, y + 3, x + 7, y + 3, x + 7, y + 7, x + 3, y + 7}; 
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
        if (Colisiones.colisionConObjetos(tiledMap, vertices)) {
            x = oldX;
            y = oldY;
        }
        
        if (Teleport.Teleport(tiledMap, vertices)) {
<<<<<<< HEAD
            cambiarMapa(game, 1); //Luego cambiaremos este 1.
        }
    }
    
=======
            cambiarMapa(game, 1); 
        }
    }
    

>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
    public void dibujar(SpriteBatch batch, Controles controles) {
        Texture jugadorTexture = texturaManager.obtenerTextura(controles);
        batch.draw(jugadorTexture, x, y, 16, 16);
    }
    
<<<<<<< HEAD
    public void cambiarMapa(GameScreen game, int nuevoIndice) {
=======
    public void cambiarMapa(MyPokemonGame game, int nuevoIndice) {
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
        game.cambiarMapa(nuevoIndice, 1015,655);
    }

    public void dispose() {
        texturaManager.dispose();
    }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


}
