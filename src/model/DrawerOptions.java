/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lucas Amorim
 */
public class DrawerOptions {

    /**
     * @return the drawPixel
     */
    public boolean shouldDrawPixel() {
        return drawPixel;
    }

    /**
     * @param drawPixel the drawPixel to set
     */
    public void setPixelMode(boolean drawPixel) {
        this.drawPixel = drawPixel;
    }

    /**
     * @return the squareSize
     */
    public double getSquareSize() {
        return squareSize;
    }

    /**
     * @param squareSize the squareSize to set
     */
    public void setSquareSize(double squareSize) {
        this.squareSize = squareSize;
    }
    
    private boolean drawPixel;
    private double squareSize;

}
