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
        tutorialImages.add(new Texture("image1.png"));
        tutorialImages.add(new Texture("image2.png"));
        tutorialImages.add(new Texture("image3.png"));
        tutorialImages.add(new Texture("image4.png"));
        tutorialImages.add(new Texture("image5.png"));
        tutorialImages.add(new Texture("image6.png"));

        currentImageIndex = 0;
        enteringName = false;
        playerName = new StringBuilder();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (currentImageIndex == 5) {
                    if (keycode == Keys.ENTER) {
                        // Continuar con el juego una vez ingresado el nombre
                        game.setScreen(new GameScreen(game));
                        dispose();
                    } else if (keycode == Keys.BACKSPACE && playerName.length() > 0) {
                        playerName.deleteCharAt(playerName.length() - 1);
                    }
                    return true;
                } else {
                    if (keycode == Keys.ENTER) {
                        if (currentImageIndex < tutorialImages.size - 1) {
                            currentImageIndex++;
                            if (currentImageIndex == 2) {
                                enteringName = true;
                            } else if (currentImageIndex > 2) {
                                enteringName = false;
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
