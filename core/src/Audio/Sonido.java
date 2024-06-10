package Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sonido {

    private Sound pressed;
    private Sound opcion;
    private Sound caminar;
    private Sound ataque;
    private Sound curar;
    private Sound error;
    
    
    public Sonido() {
  
    	 pressed = Gdx.audio.newSound(Gdx.files.internal("Audio/Presionado.mp3"));
    	 opcion = Gdx.audio.newSound(Gdx.files.internal("Audio/CambiarOpcion.mp3"));
    	 caminar = Gdx.audio.newSound(Gdx.files.internal("Audio/CaminarSonido.mp3"));
    	 ataque = Gdx.audio.newSound(Gdx.files.internal("Audio/explosion.mp3"));
    	 curar = Gdx.audio.newSound(Gdx.files.internal("Audio/health.mp3"));
    	 error = Gdx.audio.newSound(Gdx.files.internal("Audio/error.mp3"));
    	 
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
    
    public void playAtaqueSonido() {
    	ataque.play(2);
    }
    
    public void playHealth() {
    	curar.play(2);
    }
    
    public void playError() {
    	error.play(2);
    }
    
    
    
    
    
    
    
    public void dispose() {
        // Libera los recursos de música y sonido
        pressed.dispose();
        opcion.dispose();
        caminar.dispose();
    }
}
