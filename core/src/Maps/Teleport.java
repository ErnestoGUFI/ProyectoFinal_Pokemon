package Maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;

<<<<<<< HEAD
// Es un copia y pega de la capa colisiones, solo que busca en una capa diferente.

=======
>>>>>>> f4c9b56deec735be93cdb85300b78ce071b20555
public class Teleport {

    public static boolean Teleport(TiledMap tiledMap, float[] vertices) {
      
        Polygon jugadorPolygon = new Polygon(vertices);

        
        MapLayer objectLayer = tiledMap.getLayers().get("Teleport");

      
        for (RectangleMapObject objeto : objectLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle objetoRectangle = objeto.getRectangle();
            
            
            if (Intersector.overlapConvexPolygons(jugadorPolygon, rectangleToPolygon(objetoRectangle))) {
                return true; 
            }
        }    

        for (PolygonMapObject objeto : objectLayer.getObjects().getByType(PolygonMapObject.class)) {
            Polygon objetoPolygon = objeto.getPolygon();
            
           
            if (Intersector.overlapConvexPolygons(jugadorPolygon, objetoPolygon)) {
                return true; 
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