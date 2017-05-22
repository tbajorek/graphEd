/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

/**
 *
 * @author tomek
 */
public class Hstretching extends MainModule {
    protected int rmax;
    protected int rmin;
    protected int gmax;
    protected int gmin;
    protected int bmax;
    protected int bmin;
    
    @Override
    public boolean execute() {
        findMaxAndMinValues();
        eachEveryPixel();
        return true;
    }
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        color[0] = getColorValue(color[0], rmin, rmax);
        color[1] = getColorValue(color[1], gmin, gmax);
        color[2] = getColorValue(color[2], bmin, bmax);
        return color;
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed hstretching <sourcefile> <destfile>");
    }
    
    private int getColorValue(int color, int cmin, int cmax) {
        return (int)((255.0/(cmax-cmin))*(color-cmin));
    }
    
    private void findMaxAndMinValues() {
        for(int x = 0; x < source.getWidth(); ++x) {
            for( int y = 0; y < source.getHeight(); ++y) {
                int color = source.getR(x, y);
                if(rmax < color) {
                    rmax = color;
                } else if(rmin > color) {
                    rmin = color;
                }
                color = source.getG(x, y);
                if(gmax < color) {
                    gmax = color;
                } else if(gmin > color) {
                    gmin = color;
                }
                color = source.getB(x, y);
                if(bmax < color) {
                    bmax = color;
                } else if(bmin > color) {
                    bmin = color;
                }
            }
        }
    }
}
