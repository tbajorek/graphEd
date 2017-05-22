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
public class Hequalization extends MainModule {
    protected double[] rHist = new double[256];
    protected double[] gHist = new double[256];
    protected double[] bHist = new double[256];
    protected double rMin;
    protected double gMin;
    protected double bMin;
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        color[0] = getNewValue(color[0], rHist, rMin);
        color[1] = getNewValue(color[1], gHist, gMin);
        color[2] = getNewValue(color[2], bHist, bMin);
        return color;
    }
    
    @Override
    public boolean execute() {
        prepareHistogram();
        eachEveryPixel();
        return true;
    }
    
    private int getNewValue(int color, double[] colorHist, double cmin) {
        
        return (int)(((colorHist[color] - cmin) / (1.0 - cmin)) * 255);
    }
    
    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed brightness <sourcefile> <destfile> <newbrightness>");
    }
    
    private void prepareHistogram() {
        int pos = 0;
        int[] rCount = new int[256];
        int[] gCount = new int[256];
        int[] bCount = new int[256];
        double pixelsNumber = source.getHeight()*source.getWidth();
        double rSum = 0.0;
        double gSum = 0.0;
        double bSum = 0.0;
        for(int i = 0; i< 256; ++i) {
            rCount[i] = 0;
            gCount[i] = 0;
            bCount[i] = 0;
        }
        for(int x = 0; x < source.getWidth(); ++x) {
            for( int y = 0; y < source.getHeight(); ++y) {
                int[] rgb = source.getRGB(x, y);
                ++rCount[rgb[0]];
                ++gCount[rgb[1]];
                ++bCount[rgb[2]];
            }
        }
        for(int i = 0; i< 256; ++i) {
            rSum += (rCount[i]/pixelsNumber);
            gSum += (gCount[i]/pixelsNumber);
            bSum += (bCount[i]/pixelsNumber);
            rHist[i] += rSum;
            gHist[i] += gSum;
            bHist[i] += bSum;
        }
        while (Math.abs(rHist[pos]) < 0.0001) ++pos;
        rMin = rHist[pos];
        pos = 0;
        while (Math.abs(gHist[pos]) < 0.0001) ++pos;
        gMin = gHist[pos];
        pos = 0;
        while (Math.abs(bHist[pos]) < 0.0001) ++pos;
        bMin = bHist[pos];
    }
}
