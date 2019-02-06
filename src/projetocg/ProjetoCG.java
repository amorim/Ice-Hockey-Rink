package projetocg;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import drawing.ArenaRenderer;
import drawing.strategy.CircleDrawingStrategy;
import drawing.Drawer;
import drawing.strategy.LineDrawingStrategy;
import java.awt.Color;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DrawerException;
import model.DrawerOptions;
import model.Grandstand;
import model.Point;
import model.RendererOptions;

public class ProjetoCG implements GLEventListener {
    
    private GLU glu;
    private int width, height;
    private Drawer drawer;
    private ArenaRenderer renderer;
    private CircleDrawingStrategy circleStrategy;
    private LineDrawingStrategy lineStrategy;
    private boolean run = false;
    private Color color;
    private RendererOptions roptions;
    private ArrayList<Grandstand> grandstands;
    
    public ProjetoCG(GLU glu, int width, int height, Drawer drawer, RendererOptions roptions, ArrayList<Grandstand> grandstands) {
        this.glu = glu;
        this.width = width;
        this.height = height;
        this.drawer = drawer;
        this.roptions = roptions;
        this.grandstands = grandstands;
        renderer = new ArenaRenderer(roptions, circleStrategy, lineStrategy, grandstands, drawer);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        drawable.getGL().getGL2().glClear(GL2.GL_COLOR_BUFFER_BIT);
        if (!run)
            return;
        try {
            drawable.getGL().getGL2().glColor3ub((byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue());
            drawer.pushMatrix().translate(new Point(roptions.getMidX(), roptions.getMidY())).rotate(-roptions.getAngle()).translate(new Point(-roptions.getMidX(), -roptions.getMidY()));
            renderer.render();
            drawer.popMatrix();
        } catch (DrawerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        float color = 240.0f/255.0f;
        gl.glClearColor(color, color, color, 0.0f);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_BLEND);
        glu.gluOrtho2D(0, width, 0, height);
        
        drawer.setGL2(gl);
    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
        
    }
    
    public void setCircleStrategy(CircleDrawingStrategy circleStrategy) {
        this.circleStrategy = circleStrategy;
        renderer.updateCircleStrategy(circleStrategy);
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public void setLineStrategy(LineDrawingStrategy lineStrategy) {
        this.lineStrategy = lineStrategy;
        renderer.updateLineStrategy(lineStrategy);
    }

    /**
     * @return the run
     */
    public boolean isRunning() {
        return run;
    }

    /**
     * @param run the run to set
     */
    public void setDrawing(boolean run) {
        this.run = run;
    }

    void setColor(Color color) {
        this.color = color;
    }

}
