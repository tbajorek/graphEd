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
public class Grayscale extends MainModule {
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    public static int getColorValue(int[] color) {
        return (int)((color[0]+color[1]+color[2])/3.0);
    }
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        int newColor = getColorValue(color);
        color[0] = newColor;
        color[1] = newColor;
        color[2] = newColor;
        return color;
    }
    
    public static int[] getPixelInGrayscale(int[] color) {
        return (new Grayscale()).operationForEveryPixel(color);
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed grayscale <sourcefile> <destfile>");
    }
}
