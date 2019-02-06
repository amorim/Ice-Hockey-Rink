/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Lucas Amorim
 */
public class Grandstand {
    public ArrayList<Point> points;
    
    public Grandstand() {
        this.points = new ArrayList<>();
    }
    
    public void add(Point p) {
        this.points.add(p);
    }
    
    public int size() {
        return points.size();
    }
    
    public Point get(int i) {
        return points.get(i);
    }
}
