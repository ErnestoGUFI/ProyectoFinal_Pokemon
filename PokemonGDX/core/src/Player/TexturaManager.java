package Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class TexturaManager {
    private Array<Texture> arribaTextures;
    private Array<Texture> abajoTextures;
    private Array<Texture> derechaTextures;
    private Array<Texture> izquierdaTextures;

    private int arribaIndex;
    private int abajoIndex;
    private int derechaIndex;
    private int izquierdaIndex;

    public TexturaManager() {
        cargarTexturas();
        reiniciarIndices();
    }

    private void cargarTexturas() {
    	//aqui cargo todas las texturas posibles de movimiento de personaje
        arribaTextures = new Array<>();
        arribaTextures.add(new Texture("arriba.png"));
        arribaTextures.add(new Texture("arriba1.png"));
        arribaTextures.add(new Texture("arriba2.png"));
        arribaTextures.add(new Texture("arriba3.png"));

        abajoTextures = new Array<>();
        abajoTextures.add(new Texture("abajo.png"));
        abajoTextures.add(new Texture("abajo1.png"));
        abajoTextures.add(new Texture("abajo2.png"));
        abajoTextures.add(new Texture("abajo3.png"));

        derechaTextures = new Array<>();
        derechaTextures.add(new Texture("derecha.png"));
        derechaTextures.add(new Texture("derecha1.png"));
        derechaTextures.add(new Texture("derecha2.png"));
        derechaTextures.add(new Texture("derecha3.png"));

        izquierdaTextures = new Array<>();
        izquierdaTextures.add(new Texture("izquierda.png"));
        izquierdaTextures.add(new Texture("izquierda1.png"));
        izquierdaTextures.add(new Texture("izquierda2.png"));
        izquierdaTextures.add(new Texture("izquierda3.png"));

    }

    private void reiniciarIndices() {
    	//reinicio los indices de texturas
        arribaIndex = 0;
        abajoIndex = 0;
        derechaIndex = 0;
        izquierdaIndex = 0;
    }

    //primero se actuliza la textura con el movimiento llamado y luego se obtiene para pintarla en el dibujar de jugador
    //el index se le va sumando 1 para alternar entre los texturas de cada arrayu que tenemos, y asi hacer una animacion por asi decirlo
    public Texture obtenerTexturaArriba() {
        return arribaTextures.get(arribaIndex);
    }

    public Texture obtenerTexturaAbajo() {
        return abajoTextures.get(abajoIndex);
    }

    public Texture obtenerTexturaDerecha() {
        return derechaTextures.get(derechaIndex);
    }

    public Texture obtenerTexturaIzquierda() {
        return izquierdaTextures.get(izquierdaIndex);
    }

    public void actualizarIndiceArriba() {
        arribaIndex = (arribaIndex + 1) % arribaTextures.size;
    }

    public void actualizarIndiceAbajo() {
        abajoIndex = (abajoIndex + 1) % abajoTextures.size;
    }

    public void actualizarIndiceDerecha() {
        derechaIndex = (derechaIndex + 1) % derechaTextures.size;
    }

    public void actualizarIndiceIzquierda() {
        izquierdaIndex = (izquierdaIndex + 1) % izquierdaTextures.size;
    }
    
    //dependiendo de al lugar que se este llamando se obtiene una textura
    public Texture obtenerTextura(Controles controles) {
        if (controles.arriba) {
            return obtenerTexturaArriba();
        } else if (controles.abajo) {
            return obtenerTexturaAbajo();
        } else if (controles.izquierda) {
            return obtenerTexturaIzquierda();
        } else if (controles.derecha) {
            return obtenerTexturaDerecha();
        } else {
            return obtenerTexturaAbajo();
        }
    }

    public void dispose() {
        for (Texture texture : arribaTextures) {
            texture.dispose();
        }
        for (Texture texture : abajoTextures) {
            texture.dispose();
        }
        for (Texture texture : derechaTextures) {
            texture.dispose();
        }
        for (Texture texture : izquierdaTextures) {
            texture.dispose();
        }
    }
    
}
