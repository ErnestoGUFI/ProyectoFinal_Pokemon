package Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SonidoPokemon {

    
    private Sound pikachu;
    private Sound charizard;
    private Sound bulbasur;
    private Sound squirtle;
    private Sound pidgeot;
    
    public SonidoPokemon() {
  
    	 
    	 pikachu = Gdx.audio.newSound(Gdx.files.internal("Audio/pikachu.mp3"));
    	 charizard = Gdx.audio.newSound(Gdx.files.internal("Audio/charizard.mp3"));
    	 bulbasur = Gdx.audio.newSound(Gdx.files.internal("Audio/bulbasur.mp3"));
    	 squirtle = Gdx.audio.newSound(Gdx.files.internal("Audio/squirtle.mp3"));
    	 pidgeot = Gdx.audio.newSound(Gdx.files.internal("Audio/pidgeot.mp3"));
    }
     
    public void playPikachu() {
    	pikachu.play(1);
    }
    
    public void playCharizard() {
    	charizard.play(0.1f);
    }
    
    public void playBulbasur() {
    	bulbasur.play(1);
    }
    
    public void playSquirtle() {
    	squirtle.play(1);
    }
    
    public void playPidgeot() {
    	pidgeot.play(0.1f);
    }

    public void dispose() {
        // Libera los recursos de música y sonido
        pikachu.dispose();
        charizard.dispose();
        bulbasur.dispose();
        squirtle.dispose();
        pidgeot.dispose();
    }
}
