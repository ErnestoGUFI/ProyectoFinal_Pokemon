package Player;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class Controles extends InputAdapter {

    public boolean arriba, abajo, izquierda, derecha, correr;
    
    
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
                arriba = true;
                return true;
            case Keys.S:
                abajo = true;
                return true;
            case Keys.A:
                izquierda = true;
                return true;
            case Keys.D:
                derecha = true;
                return true;
            case Keys.SHIFT_LEFT:
            	correr = true;
                return true;
            case Keys.SHIFT_RIGHT:
                correr = true;
                return true;
            default:
                return false;
            
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
                arriba = false;
                return true;
            case Keys.S:
                abajo = false;
                return true;
            case Keys.A:
                izquierda = false;
                return true;
            case Keys.D:
                derecha = false;
                return true;
            case Keys.SHIFT_LEFT:
            case Keys.SHIFT_RIGHT:
                correr = false;
                return true;
            default:
                return false;
        }
    }
}
