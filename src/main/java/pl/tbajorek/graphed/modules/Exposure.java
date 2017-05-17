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
public class Exposure extends MainModule {
    protected double exposureValue;
    {
        minimumArguments = 4;
    }
    
    @Override
    public void initialize(String[] params) {
        setArguments(params[1], params[2], Double.parseDouble(params[3]));
    }
    
    public void setArguments(String readFile, String writeFile, double exposureValue) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.exposureValue = exposureValue;
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    private int getColorValue(int color, double a) {
        int newColor = (int)(a*color);
        if (newColor > 255) {
            newColor = 255;
        }
        return newColor;
    }
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        color[0] = getColorValue(color[0], exposureValue);
        color[1] = getColorValue(color[1], exposureValue);
        color[2] = getColorValue(color[2], exposureValue);
        return color;
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed exposure <sourcefile> <destfile> <exposeval>");
    }
    
}
