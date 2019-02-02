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
public class Circle {
    public Point center;
    public double radius;
    
    public Circle() {
        center = new Point();
    }
    
    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }
}
