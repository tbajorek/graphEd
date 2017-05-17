/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

import pl.tbajorek.graphed.model.Image;
import pl.tbajorek.graphed.exception.TooLessArguments;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pl.tbajorek.graphed.exception.BadArgument;
import pl.tbajorek.graphed.exception.FileNotFound;

/**
 *
 * @author tomek
 */
abstract public class MainModule implements ModuleInterface {
    protected String readFile;
    protected String writeFile;
    protected String[] params;
    protected int minimumArguments = 3;
    protected Image source = new Image();
    
    @Override
    public void initialize(String[] params) throws TooLessArguments, BadArgument {
        if (params.length < minimumArguments) {
            throw new TooLessArguments();
        }
        this.params = params;
        this.readFile = params[1];
        this.writeFile = params[2];
    }
    
    @Override
    public void loadImage() throws FileNotFound, IOException {
        source.open(readFile);
    }
    
    @Override
    public void saveResults() throws IOException {
        String[] parts = writeFile.split("\\.");
        source.save(writeFile, parts[parts.length-1]);
    }
    
    @Override
    public Image getImage() {
        return source;
    }
    
    protected void eachEveryPixel() {
        for(int x = 0; x < source.getWidth(); ++x) {
            for( int y = 0; y < source.getHeight(); ++y) {
                source.setRGB(x, y, operationForEveryPixel(source.getRGB(x, y)));
            }
        }
    }
    
    protected int[] operationForEveryPixel(int[] color) {
        return color;
    }
}
