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
public class BresenhamCircleStrategy extends CircleDrawingStrategy {

    public BresenhamCircleStrategy(Drawer drawer) {
        super(drawer);
    }

    @Override
    public void drawCircle(Circle circle, CircleDrawingConfig config) throws DrawerException {
        drawer.pushMatrix().translate(circle.center).beginShape();
        double penWidth = drawer.options.getSquareSize();
        for (int j = 0; j < penWidth; j ++) {
            int x = 0;
            double y = circle.radius + (j - (penWidth - 1) / 2.0);
            double d = (double) 5 / 4 - circle.radius;
            drawQuadrants(new Point(x, y), config);
            while (y > x) {
                if (d < 0) {
                    d += 2 * x + 3;
                    x++;
                } else {
                    d += 2 * (x - y) + 5;
                    x++;
                    y--;
                }
                drawQuadrants(new Point(x, y), config);
            }
        }
        drawer.endShape().popMatrix();
    }
    
}
