/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing.strategy;

import drawing.Drawer;
import model.DrawerException;
import model.Line;

/**
 *
 * @author Lucas Amorim
 */
public abstract class LineDrawingStrategy {
    protected Drawer drawer;
    
    public LineDrawingStrategy(Drawer drawer) {
        this.drawer = drawer;
    }
    
    public abstract void drawLine(Line line) throws DrawerException;
}
