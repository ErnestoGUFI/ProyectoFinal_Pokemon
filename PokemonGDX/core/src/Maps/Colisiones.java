package Maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Colisiones {

	// colision con mapa recibe el tilemap en tmx las coordenadas en x,y de el jugador y el tamaño de los tiles del mapa
    public static boolean colisionConMapa(TiledMap tiledMap, float x, float y, int tileWidth, int tileHeight) {
        // Convierte las coordenadas del jugador a índices de tiles
        int tileX = (int) (x / tileWidth);
        int tileY = (int) (y / tileHeight);

        // aqui se busca una capa de el mapa.tmx que se realizo, en este caso le puse de nombre colisionables a los tiles que no quiero que el jugador sea capaz de pisar
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Colisionables");
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
        return cell != null;
    }
}