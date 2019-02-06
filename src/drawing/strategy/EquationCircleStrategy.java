/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.strategy;

import drawing.Drawer;
import model.CircleDrawingConfig;
import model.Circle;
import model.DrawerException;
import model.Point;

/**
 *
 * @author Lucas Amorim
 */
public class EquationCircleStrategy extends CircleDrawingStrategy {

    public EquationCircleStrategy(Drawer drawer) {
        super(drawer);
    }

    @Override
    public void drawCircle(Circle circle, CircleDrawingConfig config) throws DrawerException {
        drawer.pushMatrix().translate(circle.center).beginShape();
        double penWidth = drawer.options.getSquareSize();
        for (int j = 0; j < penWidth; j ++) {
            double r = circle.radius + (j - (penWidth - 1) / 2.0);
            double end = r / Math.sqrt(2) + 1;
            for (double x = 0, y = r; x <= end; x++) {
                drawQuadrants(new Point(x, y), config);
                y = Math.sqrt(Math.pow(r, 2) - Math.pow(x, 2));
            }
        }
        drawer.endShape().popMatrix();
    }
    
}
