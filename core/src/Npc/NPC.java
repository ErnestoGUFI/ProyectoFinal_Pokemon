package Npc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NPC {
    private Texture texture;
    public float x, y;

    public NPC(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }
}
