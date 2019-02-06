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
public class EquationLineStrategy extends LineDrawingStrategy {

    public EquationLineStrategy(Drawer drawer) {
        super(drawer);
    }

    @Override
    public void drawLine(Line line) throws DrawerException {
        drawer.beginShape();
        boolean steep = Math.abs(line.pf.y - line.pi.y) > Math.abs(line.pf.x - line.pi.x);
        if (steep) {
            line.pi.swap();
            line.pf.swap();
        }
        if (line.pi.x > line.pf.x) {
            line.crossSwap();
        }
        double m = (line.pf.y - line.pi.y) / (line.pf.x - line.pi.x);
        for (double x = line.pi.x, y = line.pi.y; x <= line.pf.x; x++) {
            if (steep) {
                drawer.drawPoint(new Point(y, x));
            } else {
                drawer.drawPoint(new Point(x, y));
            }
            y += m;
        }
        drawer.endShape();
    }

}
