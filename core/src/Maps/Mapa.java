package Maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Mapa {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    public Mapa(String mapaPath, OrthographicCamera camera) {
        this.camera = camera;
        viewport = new FitViewport(640, 300, camera);
        viewport.apply();

        cargarMapa(mapaPath);
    }

    private void cargarMapa(String mapaPath) {
        TmxMapLoader mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load(mapaPath);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
