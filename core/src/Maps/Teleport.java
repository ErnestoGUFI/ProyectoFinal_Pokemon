package Maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Teleport {

    public static boolean TeleportMapa1(TiledMap tiledMap, float[] vertices) {
        return checkTeleport(tiledMap, vertices, "Mapa1");
    }

    public static boolean TeleportMapa2(TiledMap tiledMap, float[] vertices) {
        return checkTeleport(tiledMap, vertices, "Mapa2");
    }

    public static boolean TeleportTienda(TiledMap tiledMap, float[] vertices) {
        return checkTeleport(tiledMap, vertices, "Tienda");
    }

    public static boolean TeleportCasa(TiledMap tiledMap, float[] vertices) {
        return checkTeleport(tiledMap, vertices, "Casa");
    }
    
    public static boolean TeleportVolver(TiledMap tiledMap, float[] vertices) {
        return checkTeleport(tiledMap, vertices, "Volver");
    }

    private static boolean checkTeleport(TiledMap tiledMap, float[] vertices, String targetName) {
        Polygon jugadorPolygon = new Polygon(vertices);

        MapLayer objectLayer = tiledMap.getLayers().get("Teleport");

        for (RectangleMapObject objeto : objectLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle objetoRectangle = objeto.getRectangle();
            if (Intersector.overlapConvexPolygons(jugadorPolygon, rectangleToPolygon(objetoRectangle))) {
                if (targetName.equals(objeto.getName())) {
                    System.out.println("Nombre del teleport activado: " + objeto.getName());
                    return true;
                }
            }
        }

   
            
        

        return false;
    }

    // Método para convertir un rectángulo en un polígono
    private static Polygon rectangleToPolygon(Rectangle rectangle) {
        float[] vertices = {rectangle.x, rectangle.y,
                            rectangle.x + rectangle.width, rectangle.y,
                            rectangle.x + rectangle.width, rectangle.y + rectangle.height,
                            rectangle.x, rectangle.y + rectangle.height};
        return new Polygon(vertices);
    }
}
