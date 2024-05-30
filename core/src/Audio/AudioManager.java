package Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
	
	private Music backgroundMusic;
    private Sound buttonClickSound;
    
	public AudioManager() {
        // Carga los archivos de música y sonido
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("button_click.wav"));
    }

    public void playBackgroundMusic() {
        // Reproduce la música de fondo en un bucle
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    public void playButtonClickSound() {
        // Reproduce el sonido de clic de botón
        buttonClickSound.play();
    }

    public void dispose() {
        // Libera los recursos de música y sonido
        backgroundMusic.dispose();
        buttonClickSound.dispose();
    }
}
