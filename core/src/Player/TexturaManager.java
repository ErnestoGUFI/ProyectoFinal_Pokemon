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

    private enum UltimaDireccion {
        ARRIBA, ABAJO, DERECHA, IZQUIERDA
    }

    private UltimaDireccion ultimaDireccion;

    public TexturaManager() {
        cargarTexturas();
        reiniciarIndices();
        ultimaDireccion = UltimaDireccion.ABAJO; 
    }

    private void cargarTexturas() {
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
        arribaIndex = 0;
        abajoIndex = 0;
        derechaIndex = 0;
        izquierdaIndex = 0;
    }

    public Texture obtenerTexturaArriba() {
        ultimaDireccion = UltimaDireccion.ARRIBA;
        return arribaTextures.get(arribaIndex);
    }

    public Texture obtenerTexturaAbajo() {
        ultimaDireccion = UltimaDireccion.ABAJO;
        return abajoTextures.get(abajoIndex);
    }

    public Texture obtenerTexturaDerecha() {
        ultimaDireccion = UltimaDireccion.DERECHA;
        return derechaTextures.get(derechaIndex);
    }

    public Texture obtenerTexturaIzquierda() {
        ultimaDireccion = UltimaDireccion.IZQUIERDA;
        return izquierdaTextures.get(izquierdaIndex);
    }

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
        
            switch (ultimaDireccion) {
                case ARRIBA:
                    return arribaTextures.get(arribaIndex);
                case ABAJO:
                    return abajoTextures.get(abajoIndex);
                case DERECHA:
                    return derechaTextures.get(derechaIndex);
                case IZQUIERDA:
                    return izquierdaTextures.get(izquierdaIndex);
                default:
                    return abajoTextures.get(abajoIndex);
            }
        }
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
