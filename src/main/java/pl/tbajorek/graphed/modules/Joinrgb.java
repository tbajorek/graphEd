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
public class Joinrgb extends MainModule {
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
    
    public void setArguments(String redFile, String greenFile, String blueFile, String writeFile, String method) {
        this.writeFile = writeFile;
        this.redFile = redFile;
        this.greenFile = greenFile;
        this.blueFile = blueFile;
        this.method = method;
    }
    
    @Override
    public void loadImage() throws FileNotFound, IOException {
        redImage.open(redFile);
        greenImage.open(greenFile);
        blueImage.open(blueFile);
        source.create(redImage.getWidth(), redImage.getHeight());
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
                source.setRGB(x, y, redImage.getR(x, y), greenImage.getG(x, y), blueImage.getB(x, y));
            }
        }
    }
    
    @Override
    public void saveResults() throws IOException {
        saveOneFile(source, writeFile);
    }
    
    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed joinrgb <redfile> <greenfile> <bluefile> <writefile>");
    }
}
