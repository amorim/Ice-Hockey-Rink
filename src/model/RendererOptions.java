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
    
    public RendererOptions(Point[] startPoiints, double borderRadius) {
        this.startPoints = startPoiints;
        this.borderRadius = borderRadius;
    } 
    
    public RendererOptions() {
        
    }
}
