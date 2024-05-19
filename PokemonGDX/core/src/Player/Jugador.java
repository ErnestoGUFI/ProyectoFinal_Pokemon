package Player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import Maps.Colisiones;

public class Jugador {

    private float x;
    private float y;
    private float velocidad;
    private TexturaManager texturaManager;
    private int frameCount;
    private int maxFrameCount = 10;

    public Jugador() {
        reiniciar();
        texturaManager = new TexturaManager();
    }

    public void reiniciar() {
        x = 1000;
        y = 500;
        velocidad = 2;
    }

    public void update(Controles controles, TiledMap tiledMap, int tileWidth, int tileHeight) {
        // la vieja x y la vieja y que estaba el jugador
        // se usa para colision para devolver al personaje a su posicion anterior
        float oldX = x;
        float oldY = y;
        
        frameCount++; // Incrementa frameCount
        
        if (controles.arriba) {
            y += velocidad;
            if (frameCount >= maxFrameCount) {
                texturaManager.actualizarIndiceArriba();
                frameCount = 0; // Reinicia frameCount a 0
            }
        } else if (controles.abajo) {
            y -= velocidad;
            if (frameCount >= maxFrameCount) {
                texturaManager.actualizarIndiceAbajo();
                frameCount = 0; // Reinicia frameCount a 0
            }
        } else if (controles.izquierda) {
            x -= velocidad;
            if (frameCount >= maxFrameCount) {
                texturaManager.actualizarIndiceIzquierda();
                frameCount = 0; // Reinicia frameCount a 0
            }
        } else if (controles.derecha) {
            x += velocidad;
            if (frameCount >= maxFrameCount) {
                texturaManager.actualizarIndiceDerecha();
                frameCount = 0; // Reinicia frameCount a 0
            }
        }

        if (controles.correr) {
            velocidad = 3;
        } else {
            velocidad = 2;
        }

        if (colisionConMapa(tiledMap, x, y, tileWidth, tileHeight)) {
            x = oldX;
            y = oldY;
        }
    }

    private boolean colisionConMapa(TiledMap tiledMap, float x, float y, int tileWidth, int tileHeight) {
        return Colisiones.colisionConMapa(tiledMap, x, y, tileWidth, tileHeight);
    }

    public void dibujar(SpriteBatch batch, Controles controles) {
        batch.draw(texturaManager.obtenerTextura(controles), x, y);
    }

    public void dispose() {
        texturaManager.dispose();
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }
}