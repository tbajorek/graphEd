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
import pl.tbajorek.graphed.model.Image;

/**
 *
 * @author tomek
 */
public class Timewarp extends MainModule {
    protected Image result;
    protected int factor;
    {
        minimumArguments = 4;
    }
    
    @Override
    public void initialize(String[] params) throws TooLessArguments, BadArgument {
        checkParams(params);
        setArguments(params[1], params[2], Integer.parseInt(params[3]));
    }
    
    public void setArguments(String readFile, String writeFile, int factor) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.factor = factor;
    }
    
    @Override
    public void loadImage() throws FileNotFound, IOException {
        source.open(readFile);
        result = new Image();
        result.create(source.getWidth(), source.getHeight());
    }
    
    @Override
    protected void eachEveryPixel() {
        int distX, distY;
        double newRadius, theta, radius;
        int centerX = source.getWidth()/2;
        int centerY = source.getHeight()/2;
        int newX, newY;
        for(int x = 0; x < source.getWidth(); ++x) {
            for( int y = 0; y < source.getHeight(); ++y) {
                distX = x - centerX;
                distY = y - centerY;
                theta = Math.atan2(distY, distX);
                radius = Math.sqrt(distX * distX + distY * distY);
                newRadius = Math.sqrt(radius) * factor;
                newX = (int)(centerX + (newRadius * Math.cos(theta)));
                newY = (int)(centerY + (newRadius * Math.sin(theta)));
                if(isCorrectPoint(newX, newY)) {
                    result.setRGB(x, y, source.getRGB(newX, newY));
                }
            }
        }
    }
    
    private boolean isCorrectPoint(int x, int y) {
        return x >= 0 &&
               y >= 0 &&
               x < source.getWidth() &&
               y < source.getHeight();
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    @Override
    public void saveResults() throws IOException {
        saveOneFile(result, writeFile);
    }
    
    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed brightness <sourcefile> <destfile> <newbrightness>");
    }
}
