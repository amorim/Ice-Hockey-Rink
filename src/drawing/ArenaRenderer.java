/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import drawing.strategy.CircleDrawingStrategy;
import drawing.strategy.LineDrawingStrategy;
import java.awt.MouseInfo;
import java.util.ArrayList;
import model.CircleDrawingConfig;
import model.Circle;
import model.DrawerException;
import model.Grandstand;
import model.Line;
import model.Point;
import model.RendererOptions;

/**
 *
 * @author Lucas Amorim
 */
public class ArenaRenderer {
    private CircleDrawingStrategy circleStrategy;
    private LineDrawingStrategy lineStrategy;
    private RendererOptions options;
    private double zoneY = 0;
    private ArrayList<Grandstand> grandstands;
    Drawer drawer;
    
    public ArenaRenderer(RendererOptions options, CircleDrawingStrategy circleStrategy, LineDrawingStrategy lineStrategy,
            ArrayList<Grandstand> grandstands, Drawer drawer) {
        this.circleStrategy = circleStrategy;
        this.lineStrategy = lineStrategy;
        this.options = options;
        this.grandstands = grandstands;
        this.drawer = drawer;
    }
    
    
    
    private void borders() throws DrawerException {
        // draw arena lines
        Point p = new Point(options.startPoints[0].x + options.borderRadius, options.startPoints[0].y);
        Point p2 = new Point(options.startPoints[1].x - options.borderRadius, options.startPoints[0].y);
        lineStrategy.drawLine(new Line(p, p2));
        p = new Point(options.startPoints[1].x, options.startPoints[0].y - options.borderRadius);
        p2 = new Point(options.startPoints[1].x, options.startPoints[1].y + options.borderRadius);
        p2.y = options.startPoints[1].y + options.borderRadius;
        lineStrategy.drawLine(new Line(p, p2));
        p = new Point(options.startPoints[1].x - options.borderRadius, options.startPoints[1].y);
        p2 = new Point(options.startPoints[0].x + options.borderRadius, options.startPoints[1].y);
        lineStrategy.drawLine(new Line(p, p2));
        p = new Point(options.startPoints[0].x, options.startPoints[1].y + options.borderRadius);
        p2 = new Point(options.startPoints[0].x, options.startPoints[0].y - options.borderRadius);
        lineStrategy.drawLine(new Line(p, p2));
        // draw round borders
        Circle c = new Circle();
        c.radius = options.borderRadius;
        c.center.x = options.startPoints[1].x - c.radius;
        c.center.y = options.startPoints[0].y - c.radius;
        CircleDrawingConfig config = new CircleDrawingConfig();
        config.drawQuadrant(1);
        circleStrategy.drawCircle(c, config);
        c.center.x = options.startPoints[0].x + c.radius;
        config.reset().drawQuadrant(2);
        circleStrategy.drawCircle(c, config);
        c.center.y = options.startPoints[1].y + c.radius;
        config.reset().drawQuadrant(3);
        circleStrategy.drawCircle(c, config);
        c.center.x = options.startPoints[1].x - c.radius;
        config.reset().drawQuadrant(4);
        circleStrategy.drawCircle(c, config);
    }
    
    private void lines() throws DrawerException {
        // center line
        double mid = options.getMidY();
        Point p = new Point(options.startPoints[0].x, mid);
        Point p2 = new Point(options.startPoints[1].x, mid);
        lineStrategy.drawLine(new Line(p, p2));
        // goal lines
        p = new Point(options.startPoints[0].x, options.startPoints[0].y - options.borderRadius);
        p2 = new Point(options.startPoints[1].x, options.startPoints[0].y - options.borderRadius);
        lineStrategy.drawLine(new Line(p, p2));
        p = new Point(options.startPoints[0].x, options.startPoints[1].y + options.borderRadius);
        p2 = new Point(options.startPoints[1].x, options.startPoints[1].y + options.borderRadius);
        lineStrategy.drawLine(new Line(p, p2));
        // zone lines
        double blueYPos1 = (((options.startPoints[0].y - options.borderRadius + mid) / 2) + mid) / 2;
        zoneY = blueYPos1;
        p = new Point(options.startPoints[0].x, blueYPos1);
        p2 = new Point(options.startPoints[1].x, blueYPos1);
        lineStrategy.drawLine(new Line(p, p2));
        double blueYPos2 = (((options.startPoints[1].y + options.borderRadius + mid) / 2) + mid) / 2;
        p = new Point(options.startPoints[0].x, blueYPos2);
        p2 = new Point(options.startPoints[1].x, blueYPos2);
        lineStrategy.drawLine(new Line(p, p2));
    }
    
    private void circles() throws DrawerException {
        CircleDrawingConfig config = new CircleDrawingConfig();
        config.drawEntireCircle();
        double radius = (zoneY + options.getMidY()) / 2 - options.getMidY();
        Circle c = new Circle(new Point(options.getMidX(), options.getMidY()), radius);
        circleStrategy.drawCircle(c, config);
        double goalLineOffsetLow = options.startPoints[1].y + options.borderRadius;
        double goalLineOffsetHigh = options.startPoints[0].y - options.borderRadius;
        double goalDifferenceYLow = (options.getMidY() - (zoneY - options.getMidY()) - goalLineOffsetLow) / 3;
        double goalDifferenceYHigh = (goalLineOffsetHigh - zoneY) / 3;
        double lowCirclesY = goalLineOffsetLow + goalDifferenceYLow;
        double highCirclesY = goalLineOffsetHigh - goalDifferenceYHigh;
        double circle1X = (options.getMidX() + options.startPoints[0].x) / 2;
        double circle2X = (options.getMidX() + options.startPoints[1].x) / 2;
        c.center = new Point(circle1X, lowCirclesY);
        circleStrategy.drawCircle(c, config);
        c.center = new Point(circle2X, lowCirclesY);
        circleStrategy.drawCircle(c, config);
        c.center = new Point(circle1X, highCirclesY);
        circleStrategy.drawCircle(c, config);
        c.center = new Point(circle2X, highCirclesY);
        circleStrategy.drawCircle(c, config);
        config.reset().drawQuadrant(1).drawQuadrant(2);
        c.center = new Point(options.getMidX(), goalLineOffsetLow);
        circleStrategy.drawCircle(c, config);
        config.reset().drawQuadrant(3).drawQuadrant(4);
        c.center = new Point(options.getMidX(), goalLineOffsetHigh);
        circleStrategy.drawCircle(c, config);
    }
    
    private void grandstands() throws DrawerException {
        for (Grandstand g: grandstands) {
            if (g.size() == 0)
                continue;
            ArrayList<Point> points = g.points;
            for (int i = 1; i < points.size(); i++) {
                lineStrategy.drawLine(new Line(points.get(i - 1).clonePoint(), points.get(i).clonePoint()));
            }
        }
    }
    
    private void grandStandGuide() throws DrawerException {
        if (!options.shouldDrawGuide())
            return;
        if (grandstands.isEmpty())
            return;
        Grandstand g = grandstands.get(grandstands.size() - 1);
        if (g.size() == 0)
            return;
        Point p = g.get(g.size() - 1);      
        drawer.color(options.getColor(), (byte)64);
        lineStrategy.drawLine(new Line(p.clonePoint(), options.getCursorPos().clonePoint()));
        drawer.color(options.getColor(), (byte)0);
    }
    
    public void render() throws DrawerException {
        grandstands();
        borders();
        lines();
        circles();
        grandStandGuide();
    }

    public void updateLineStrategy(LineDrawingStrategy lineStrategy) {
        this.lineStrategy = lineStrategy;
    }
    
    public void updateCircleStrategy(CircleDrawingStrategy circleStrategy) {
        this.circleStrategy = circleStrategy;
    }

}
