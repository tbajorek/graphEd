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
public class Dividergb extends MainModule {
    protected Image redImage;
    protected Image greenImage;
    protected Image blueImage;
    protected String redFile;
    protected String greenFile;
    protected String blueFile;
    protected String method;
    {
        minimumArguments = 5;
        redImage = new Image();
        greenImage = new Image();
        blueImage = new Image();
        
    }
    
    @Override
    public void initialize(String[] params) throws BadArgument, TooLessArguments {
        checkParams(params);
        this.params = params;
        String cmd;
        if(params.length == 6) {
            cmd = params[5];
        } else {
            cmd = "gray";
        }
        setArguments(params[1], params[2], params[3], params[4], cmd);
    }
    
    public void setArguments(String readFile, String redFile, String greenFile, String blueFile, String method) {
        this.readFile = readFile;
        this.redFile = redFile;
        this.greenFile = greenFile;
        this.blueFile = blueFile;
        this.method = method;
    }
    
    @Override
    public void loadImage() throws FileNotFound, IOException {
        super.loadImage();
        redImage.create(source.getWidth(), source.getHeight());
        greenImage.create(source.getWidth(), source.getHeight());
        blueImage.create(source.getWidth(), source.getHeight());
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    @Override
    protected void eachEveryPixel() {
        for(int x = 0; x < source.getWidth(); ++x) {
            for( int y = 0; y < source.getHeight(); ++y) {
                int[] rgb = source.getRGB(x, y);
                switch(method) {
                    case "gray":
                        redImage.setRGB(x, y, rgb[0], rgb[0], rgb[0]);
                        greenImage.setRGB(x, y, rgb[1], rgb[1], rgb[1]);
                        blueImage.setRGB(x, y, rgb[2], rgb[2], rgb[2]);
                        break;
                    case "color":
                        redImage.setRGB(x, y, rgb[0], 0, 0);
                        greenImage.setRGB(x, y, 0, rgb[1], 0);
                        blueImage.setRGB(x, y, 0, 0, rgb[2]);
                }
            }
        }
    }
    
    @Override
    public void saveResults() throws IOException {
        saveOneFile(redImage, redFile);
        saveOneFile(greenImage, greenFile);
        saveOneFile(blueImage, blueFile);
    }
    
    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed dividergb <sourcefile> <redfile> <greenfile> <bluefile> [<command>]");
        System.out.println("<commmand> = \"gray\" - result images will be in saved a grayscale");
        System.out.println("<commmand> = \"color\" - result images will be colored in RGB colors");
    }
}
