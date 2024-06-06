package Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sonido {

    private Sound pressed;
    private Sound opcion;
    private Sound caminar;
    
    public Sonido() {
  
    	 pressed = Gdx.audio.newSound(Gdx.files.internal("Presionado.mp3"));
    	 opcion = Gdx.audio.newSound(Gdx.files.internal("CambiarOpcion.mp3"));
    	 caminar = Gdx.audio.newSound(Gdx.files.internal("CaminarSonido.mp3"));
    }
    
    public void playPressedSound() {
    	
    	pressed.play(2);
    }
    
    public void playCambiarOpcion() {
    	
    	opcion.play(2);
    }
    
    public void playCaminarSonido() {
    	caminar.play(1);
    }
    
    public void stopCaminarSonido() {
    	caminar.stop();
    }
    
    public void dispose() {
        // Libera los recursos de música y sonido
        pressed.dispose();
        opcion.dispose();
        caminar.dispose();
    }
}
