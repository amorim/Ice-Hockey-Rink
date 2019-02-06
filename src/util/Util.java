/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import model.Grandstand;
import model.Line;
import model.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

/**
 *
 * @author Lucas Amorim
 */
public class Util {

    public static boolean collision(ArrayList<Grandstand> grandstands, Line lineToBeAdded, Point[] rinkPoints, boolean closingLine) {
        GeometryFactory gf = new GeometryFactory();
        double bufferDistance = 0.0001 * 0.0001 * 0.0001;
        LineString baseLineString = gf.createLineString(convertLineToCoordinate(lineToBeAdded));
        Polygon rink = gf.createPolygon(convertRinkToCoordinate(rinkPoints));
        if (baseLineString.buffer(bufferDistance).intersects(rink))
            return true;
        for (int i = 0; i < grandstands.size(); i++) {
            Grandstand g = grandstands.get(i);
            if (i == grandstands.size() - 1) {
                for (int j = (closingLine ? 1 : 0); j < g.size() - 2; j++) {
                    LineString lineString1 = gf.createLineString(convertLineToCoordinate(new Line(g.get(j), g.get(j + 1))));
                    if (lineString1.buffer(bufferDistance).intersects(baseLineString)) {
                        return true;
                    }
                }
                return false;
            }
            for (int j = 0; j < g.size() - 1; j++) {
                LineString lineString1 = gf.createLineString(convertLineToCoordinate(new Line(g.get(j), g.get(j + 1))));
                if (lineString1.buffer(bufferDistance).intersects(baseLineString)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Coordinate[] convertLineToCoordinate(Line line) {
        return new Coordinate[]{new Coordinate(line.pi.x, line.pi.y), new Coordinate(line.pf.x, line.pf.y)};

    }
    
    public static Coordinate[] convertRinkToCoordinate(Point[] rinkPoints) {
        return new Coordinate[] { new Coordinate(rinkPoints[0].x, rinkPoints[0].y), new Coordinate(rinkPoints[1].x, rinkPoints[0].y), new Coordinate(rinkPoints[1].x, rinkPoints[1].y), new Coordinate(rinkPoints[0].x, rinkPoints[1].y), new Coordinate(rinkPoints[0].x, rinkPoints[0].y)};
    }
}
