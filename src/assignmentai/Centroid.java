/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentai;

/**
 *
 * @author Ryan Avila
 */
public class Centroid {
    private double mX = 0.0;
    private double mY = 0.0;
        
        public Centroid()
        {
            return;
        }
        
        public Centroid(double newX, double newY)
        {
            this.mX = newX;
            this.mY = newY;
            return;
        }
        
        public void X(double newX)
        {
            this.mX = newX;
            return;
        }
        
        public double X()
        {
            return this.mX;
        }
        
        public void Y(double newY)
        {
            this.mY = newY;
            return;
        }
        
        public double Y()
        {
            return this.mY;
        }
}
