/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import model.Circle;
import model.DrawerException;
import model.Point;

/**
 *
 * @author Lucas Amorim
 */
public abstract class CircleDrawingStrategy {

    protected Drawer drawer;
    private Point point;

    public CircleDrawingStrategy(Drawer drawer) {
        this.drawer = drawer;
    }
    
    protected CircleDrawingStrategy setCirclePoint(Point p) {
        this.point = p;
        return this;
    }
    
    private void drawFirstQuadrant() throws DrawerException {
        drawer.drawPoint(point);
        drawer.drawPoint(new Point(point.y, point.x));
    }
    
    private void drawSecondQuadrant() throws DrawerException {
        drawer.drawPoint(new Point(-point.x, point.y));
        drawer.drawPoint(new Point(-point.y, point.x));
    }
    
    private void drawThirdQuadrant() throws DrawerException {
        drawer.drawPoint(new Point(-point.x, -point.y));
        drawer.drawPoint(new Point(-point.y, -point.x));
    }
    
    private void drawFourthQuadrant() throws DrawerException {
        drawer.drawPoint(new Point(point.x, -point.y));
        drawer.drawPoint(new Point(point.y, -point.x));
    }
    
    protected void drawQuadrants(Point circlePoint, CircleDrawingConfig config) throws DrawerException {
        setCirclePoint(circlePoint);
        if (config.shouldDrawNthQuadrant(1))
            drawFirstQuadrant();
        if (config.shouldDrawNthQuadrant(2))
            drawSecondQuadrant();
        if (config.shouldDrawNthQuadrant(3))
            drawThirdQuadrant();
        if (config.shouldDrawNthQuadrant(4))
            drawFourthQuadrant();
    }
    
    
    
    

    public abstract void drawCircle(Circle circle, CircleDrawingConfig config) throws DrawerException;
}
