/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

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
        drawer.beginShape();
        Line aux = line.cloneLine();
        boolean steep = Math.abs(line.pf.y - line.pi.y) > Math.abs(line.pf.x - line.pi.x);
        if (steep) {
            line.pi.swap();
            line.pf.swap();
        }
        if (line.pi.x > line.pf.x) {
            line.crossSwap();
        }
        int dx, dy, E, NE, d, yStep = line.pi.y > line.pf.y ? -1 : 1;
        dx = (int) (line.pf.x - line.pi.x);
        dy = (int) (line.pf.y - line.pi.y);
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
        line = aux;
        drawer.endShape();
    }

}
