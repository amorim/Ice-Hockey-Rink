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
public class Line {
    public Point pi, pf;
    
    public Line() {
        
    }
    
    public Line(Point pi, Point pf) {
        this.pi = pi;
        this.pf = pf;
    }
    
    public Line cloneLine() {
        return new Line(pi.clonePoint(), pf.clonePoint());
    }
    
    public void crossSwap() {
        double temp = pi.x;
        pi.x = pf.x;
        pf.x = temp;
        temp = pi.y;
        pi.y = pf.y;
        pf.y = temp;
    }
}
