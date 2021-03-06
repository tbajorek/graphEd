/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

import pl.tbajorek.graphed.exception.BadArgument;
import pl.tbajorek.graphed.exception.TooLessArguments;

/**
 *
 * @author tomek
 */
public class Contrast extends MainModule {
    protected double contrast;
    {
        minimumArguments = 4;
    }
    
    @Override
    public void initialize(String[] params) throws TooLessArguments, BadArgument {
        checkParams(params);
        setArguments(params[1], params[2], Double.parseDouble(params[3]));
    }
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        color[0] = getNewValue(color[0], contrast);
        color[1] = getNewValue(color[1], contrast);
        color[2] = getNewValue(color[2], contrast);
        return color;
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    public void setArguments(String readFile, String writeFile, double contrast) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.contrast = contrast;
    }
    
    private int getNewValue(int color, double contrast) {
        int newValue = (int)(contrast*(color-127)+127);
        if (newValue > 255) {
            newValue = 255;
        } else if(newValue < 0) {
            newValue = 0;
        }
        return newValue;
    }
    
    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed contrast <sourcefile> <destfile> <newcontrast>");
    }
}
