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
public class Brightness extends MainModule {
    protected int brightness;
    {
        minimumArguments = 4;
    }
    
    @Override
    public void initialize(String[] params) throws TooLessArguments, BadArgument {
        checkParams(params);
        setArguments(params[1], params[2], Integer.parseInt(params[3]));
    }
    
    public void setArguments(String readFile, String writeFile, int brightness) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.brightness = brightness;
    }
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        color[0] = getNewValue(color[0], brightness);
        color[1] = getNewValue(color[1], brightness);
        color[2] = getNewValue(color[2], brightness);
        return color;
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    private int getNewValue(int color, int brightness) {
        int newValue = color + brightness;
        if (newValue > 255) {
            newValue = 255;
        } else if(newValue < 0) {
            newValue = 0;
        }
        return newValue;
    }
    
    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed brightness <sourcefile> <destfile> <newbrightness>");
    }
}
