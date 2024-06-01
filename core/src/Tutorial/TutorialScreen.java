package Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

    public TutorialScreen(MyPokemonGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyPokemonGame.width, MyPokemonGame.height);

        // Cargar las imágenes del tutorial
        tutorialImages = new Array<>();
        tutorialImages.add(new Texture("image1.png"));
        tutorialImages.add(new Texture("image2.png"));
        tutorialImages.add(new Texture("image3.png"));
        tutorialImages.add(new Texture("image4.png"));
        tutorialImages.add(new Texture("image5.png"));
        tutorialImages.add(new Texture("image6.png"));

        currentImageIndex = 0;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Keys.ENTER) {
                    if (currentImageIndex < tutorialImages.size - 1) {
                        currentImageIndex++;
                    } else {
                        game.setScreen(new GameScreen(game));
                        dispose();
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
        game.batch.draw(tutorialImages.get(currentImageIndex), 0, 0, MyPokemonGame.width, MyPokemonGame.height);
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
    }
}