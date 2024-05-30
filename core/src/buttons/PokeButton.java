package buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class PokeButton extends Game {
    Stage stage;
    TextButton button;
    TextButtonStyle textButtonStyle;
    BitmapFont font;

    @Override
    public void create() {      
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        // No asignar texturas para que el botón sea invisible
        textButtonStyle.up = null;
        textButtonStyle.down = null;
        textButtonStyle.checked = null;
        button = new TextButton("Button1", textButtonStyle);
        stage.addActor(button);
    }

    @Override
    public void render() {      
        super.render();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
}

