/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

import java.io.IOException;
import pl.tbajorek.graphed.exception.BadArgument;
import pl.tbajorek.graphed.exception.FileNotFound;
import pl.tbajorek.graphed.exception.TooLessArguments;

/**
 *
 * @author tomek
 */
public class Sepia extends MainModule {
    protected int sepia;
    {
        minimumArguments = 4;
    }
    
    @Override
    public void initialize(String[] params) throws TooLessArguments, BadArgument {
        super.initialize(params);
        setArguments(params[1], params[2], Integer.parseInt(params[3]));
    }
    
    @Override
    public void loadImage() throws FileNotFound, IOException {
        try {
            Grayscale grayscaleModule = new Grayscale();
            grayscaleModule.initialize(params);
            grayscaleModule.loadImage();
            grayscaleModule.execute();
            source = grayscaleModule.getImage();
        } catch (TooLessArguments | BadArgument ex) {}
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    @Override
    protected int[] operationForEveryPixel(int[] color) {
        color[0] = getColor(color[0], 2*sepia);
        color[1] = getColor(color[1], sepia);
        return color;
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed sepia <sourcefile> <destfile> <sepia>");
    }

    public void setArguments(String readFile, String writeFile, int sepia) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.sepia = sepia;
    }
    
    private int getColor(int color, int sepiaValue) {
        int newColor = color+sepiaValue;
        if (newColor > 255) {
            newColor = 255;
        }
        return newColor;
    }
}
