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
public class RendererOptions {
    public Point[] startPoints;
    public double borderRadius;
    private int angle;
    
    public RendererOptions(Point[] startPoiints, double borderRadius) {
        this.startPoints = startPoiints;
        this.borderRadius = borderRadius;
    } 
    
    public RendererOptions() {
        
    }
    
    public double getMidX() {
        return (startPoints[0].x + startPoints[1].x) / 2;
    }
    
    public double getMidY() {
        return (startPoints[0].y + startPoints[1].y) / 2;
    }

    /**
     * @return the angle
     */
    public int getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(int angle) {
        this.angle = angle;
    }
}
