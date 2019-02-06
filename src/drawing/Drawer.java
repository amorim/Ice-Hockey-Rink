/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

import model.DrawerException;
import com.jogamp.opengl.GL2;
import java.awt.Color;
import model.DrawerOptions;
import model.Point;

/**
 *
 * @author Lucas Amorim
 * Low-level class for drawing using GL2 Object
 */
public class Drawer {
    private GL2 gl;
    private boolean drawingShapeNow = false;
    private DrawerOptions options;
    
    public Drawer(DrawerOptions options) {
        this.options = options;
    }
    
    public void setGL2(GL2 gl) {
        this.gl = gl;
    }
    
    
    public Drawer beginShape() throws DrawerException {
        if (drawingShapeNow)
            throw new DrawerException("You are already drawing a shape, please finish it before creating other shapes.");        
        // gl.glPointSize((float) options.getSquareSize());
        gl.glBegin(GL2.GL_POINTS);
        drawingShapeNow = true;
        return this;
    }
    
    public Drawer drawPoint(Point p) throws DrawerException {
        if (!drawingShapeNow)
            throw new DrawerException("Please call beginShape before drawing.");
        gl.glVertex2d(p.x, p.y);
        return this;
    }
    
    public Drawer endShape() throws DrawerException {
        if (!drawingShapeNow)
            throw new DrawerException("Please call beginShape before ending a drawing.");
        gl.glEnd();
        drawingShapeNow = false;
        return this;
    }
    
    public Drawer pushMatrix() throws DrawerException {
        if (drawingShapeNow)
            throw new DrawerException("Can't operate transformation matrices while drawing is in course.");
        gl.glPushMatrix();
        return this;
    }
    
    public Drawer popMatrix() throws DrawerException {
        if (drawingShapeNow)
            throw new DrawerException("Can't operate transformation matrices while drawing is in course.");
        gl.glPopMatrix();
        return this;
    }
    
    public Drawer translate(Point p) throws DrawerException {
        if (drawingShapeNow)
            throw new DrawerException("Can't operate transformation matrices while drawing is in course.");
        gl.glTranslated(p.x, p.y, 0);
        return this;
    }
    
    public Drawer rotate(double angle) throws DrawerException {
        if (drawingShapeNow)
            throw new DrawerException("Can't operate transformation matrices while drawing is in course.");
        gl.glRotated(angle, 0, 0, 1);
        return this;
    }
    
    public Drawer color(Color color, byte alpha) throws DrawerException {
        if (drawingShapeNow)
            throw new DrawerException("Can't change color while drawing is in course.");
        gl.glColor4ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getRed(), alpha);
        return this;
    }
    
    
}
