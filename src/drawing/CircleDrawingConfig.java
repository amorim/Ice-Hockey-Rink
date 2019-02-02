/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawing;

/**
 *
 * @author Lucas Amorim
 */
public class CircleDrawingConfig {
    private boolean[] quadrants;
    
    public CircleDrawingConfig() {
        quadrants = new boolean[4];
    }
    
    public void drawEntireCircle() {
        for (int i = 0; i < 4; i++)
            quadrants[i] = true;
    }
    
    public CircleDrawingConfig drawQuadrant(int i) {
        quadrants[i - 1] = true;
        return this;
    }
    
    public boolean shouldDrawNthQuadrant(int i) {
        return quadrants[i - 1];
    }
    
    public CircleDrawingConfig reset() {
        quadrants = new boolean[4];
        return this;
    }
}
