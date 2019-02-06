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
public class Point {
    public double x, y;
    
    public Point() {
        
    }
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point clonePoint() {
        return new Point(x, y);
    }
    
    public void swap() {
        double temp = this.y;
        this.y = this.x;
        this.x = temp;
    }
}
