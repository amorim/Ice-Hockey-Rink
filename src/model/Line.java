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
    public double alpha;
    
    public Line() {
        alpha = 0;
    }
    
    public Line(Point pi, Point pf) {
        this.pi = pi;
        this.pf = pf;
        alpha = 0;
    }
    
    public Line(Point pi, Point pf, double alpha) {
        this.pi = pi;
        this.pf = pf;
        this.alpha = alpha;
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
