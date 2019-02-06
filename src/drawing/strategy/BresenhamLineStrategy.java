/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.strategy;

import drawing.Drawer;
import model.DrawerException;
import model.Line;
import model.Point;

/**
 *
 * @author Lucas Amorim
 */
public class BresenhamLineStrategy extends LineDrawingStrategy {

    public BresenhamLineStrategy(Drawer drawer) {
        super(drawer);
    }

    @Override
    public void drawLine(Line line) throws DrawerException {
        double t = Math.atan(-(line.pi.x - line.pf.x) / (line.pi.y - line.pf.y));
        Point perpendicularVector = new Point(Math.cos(t), Math.sin(t));
        double penWidth = drawer.options.getSquareSize();
        drawer.beginShape();
        for (int j = 0; j < penWidth; j ++) {
            Line aux = line.cloneLine();
            line.pi.x += (j - (penWidth - 1) / 2.0) * perpendicularVector.x;
            line.pf.x += (j - (penWidth - 1) / 2.0) * perpendicularVector.x;
            line.pi.y += (j - (penWidth - 1) / 2.0) * perpendicularVector.y;
            line.pf.y += (j - (penWidth - 1) / 2.0) * perpendicularVector.y;
            boolean steep = Math.abs(line.pf.y - line.pi.y) > Math.abs(line.pf.x - line.pi.x);
            if (steep) {
                line.pi.swap();
                line.pf.swap();
            }
            if (line.pi.x > line.pf.x) {
                line.crossSwap();
            }
            double dx, dy, E, NE, d, yStep = (line.pi.y > line.pf.y) ? -1 : 1;
            dx = (line.pf.x - line.pi.x);
            dy = Math.abs(line.pf.y - line.pi.y);
            d = 2 * dy - dx;
            E = 2 * dy;
            NE = 2 * (dy - dx);
            double x = line.pi.x, y = line.pi.y;
            if (steep) {
                drawer.drawPoint(new Point(y, x));
            } else {
                drawer.drawPoint(new Point(x, y));
            }
            while (x < line.pf.x) {
                if (d <= 0) {
                    d += E;
                    x++;
                } else {
                    d += NE;
                    x++;
                    y += yStep;
                }
                if (steep) {
                    drawer.drawPoint(new Point(y, x));
                } else {
                    drawer.drawPoint(new Point(x, y));
                }
            }
            line = aux.cloneLine();
        }
        drawer.endShape();
    }

}
