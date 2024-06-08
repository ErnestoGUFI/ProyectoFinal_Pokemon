package Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Musica {
	
	private Music MenuMusic;
	private Music MapMusic;
    
    public Musica() {
    	
    	 MenuMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/MusicaMenu.mp3"));
    	 MapMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/MusicaMapa.mp3"));
    }
    
    public void playMenuMusic() {
        MenuMusic.setLooping(true);
        MenuMusic.play();
    }
    
    public void stopMenuMusic() {
    	if(MenuMusic.isPlaying() && MenuMusic != null) {
    		MenuMusic.stop();
    	}
    }
    
    public void playMapMusic() {
    	MapMusic.setVolume(1);
        MapMusic.setLooping(true);      
        MapMusic.play();
    }
    
    public void stopMapMusic() {
    	if(MapMusic.isPlaying() && MapMusic != null) {
    		MapMusic.stop();
    	}
    }
    
    public void pauseMapMusic() {
    	if(MapMusic.isPlaying() && MapMusic != null) {
    		MapMusic.pause();
    	}
    }
    
    public void dispose() {
        // Libera los recursos de música y sonido
        MenuMusic.dispose();
        MapMusic.dispose();
    }
}
