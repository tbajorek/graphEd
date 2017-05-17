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
public class Gamma extends MainModule {
    protected double gamma;
    {
        minimumArguments = 4;
    }
    
    @Override
    public void initialize(String[] params) {
        setArguments(params[1], params[2], Double.parseDouble(params[3]));
    }
    
    public void setArguments(String readFile, String writeFile, double gamma) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.gamma = 1.0/gamma;
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    private int getColorValue(int color, double gamma) {
        int newColor = (int)Math.pow(255.0 * (color / 255.0), gamma);
        if (newColor > 255) {
            newColor = 255;
        }
        return newColor;
    }
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        color[0] = getColorValue(color[0], gamma);
        color[1] = getColorValue(color[1], gamma);
        color[2] = getColorValue(color[2], gamma);
        return color;
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed exposure <sourcefile> <destfile> <gamma>");
    }
    
}
