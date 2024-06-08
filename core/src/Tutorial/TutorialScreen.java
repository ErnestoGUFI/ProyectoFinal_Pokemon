package Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.pokemon.game.MyPokemonGame;
import com.pokemon.game.screens.GameScreen;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import DataBase.DataBase;

public class TutorialScreen extends ScreenAdapter {
    private MyPokemonGame game;
    private OrthographicCamera camera;
    private Array<Texture> tutorialImages;
    private int currentImageIndex;
    private boolean enteringName;
    private StringBuilder playerName;
    private BitmapFont font;

    public TutorialScreen(MyPokemonGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyPokemonGame.width, MyPokemonGame.height);

        // Crear una nueva fuente con tamaño y color personalizados
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2.0f);

        // Cargar las imágenes del tutorial
        tutorialImages = new Array<>();
        tutorialImages.add(new Texture("Screen/image1.png"));
        tutorialImages.add(new Texture("Screen/image2.png"));
        tutorialImages.add(new Texture("Screen/image3.png"));
        tutorialImages.add(new Texture("Screen/image4.png"));
        tutorialImages.add(new Texture("Screen/image5.png"));
        tutorialImages.add(new Texture("Screen/image6.png"));

        currentImageIndex = 0;
        enteringName = false;
        playerName = new StringBuilder();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (enteringName) {
                    if (keycode == Keys.ENTER && playerName.length() > 0) {
                        enteringName = false;
                        currentImageIndex++;
                    } else if (keycode == Keys.BACKSPACE && playerName.length() > 0) {
                        playerName.deleteCharAt(playerName.length() - 1);
                    }
                } else {
                    if (keycode == Keys.ENTER) {
                        if (currentImageIndex == 2 && playerName.length() == 0) {
                            // No avanzar si no se ha ingresado un nombre en la pantalla 2
                            return true;
                        } else if (currentImageIndex < tutorialImages.size - 1) {
                            currentImageIndex++;
                            if (currentImageIndex == 2) {
                                enteringName = true;
                            }
                        } else {
                            // Guardar el nombre y avanzar a la pantalla del juego en la última imagen
                        	if (currentImageIndex == tutorialImages.size - 1) {
                        	    DataBase.saveUserName(playerName.toString());
                        	    // Guardar el puntaje inicial (0) para el jugador
                        	    DataBase.saveScore(playerName.toString(), 0);
                        	    game.setScreen(new GameScreen(game, playerName.toString())); // Pasar el nombre del jugador a la GameScreen
                        	    dispose();
                        	}
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (enteringName) {
                    if (playerName.length() < 20 && (Character.isLetterOrDigit(character) || Character.isSpaceChar(character))) {
                        playerName.append(character);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(tutorialImages.get(currentImageIndex), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (enteringName) {
            font.draw(game.batch, playerName.toString(), 480, 123);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        for (Texture texture : tutorialImages) {
            texture.dispose();
        }
        font.dispose();
    }
}
