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

public class Colisiones {

    public static boolean colisionConObjetos(TiledMap tiledMap, float[] vertices) {
      
        Polygon jugadorPolygon = new Polygon(vertices);

        //Buscar una capa especifica en el mapa.tmx
        MapLayer objectLayer = tiledMap.getLayers().get("Objetos");

        //Iterar entre los objetos que estan en la capa anteriormente obtenida
        for (RectangleMapObject objeto : objectLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle objetoRectangle = objeto.getRectangle();
            
            //Si poligonos del jugador colisionan con los poligonos del objeto, regresa true
            if (Intersector.overlapConvexPolygons(jugadorPolygon, rectangleToPolygon(objetoRectangle))) {
                return true; 
            }
        }    
        ////Iterar entre los rectangulos que estan en la capa anteriormente obtenida
        for (PolygonMapObject objeto : objectLayer.getObjects().getByType(PolygonMapObject.class)) {
            Polygon objetoPolygon = objeto.getPolygon();
            
            //Si el poligono del jugador colisiona con algun rectangulo, regresa true
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



