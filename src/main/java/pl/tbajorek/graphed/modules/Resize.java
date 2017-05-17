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
 *  @todo nie działa
 * @author tomek
 */
public class Resize extends MainModule {
    protected final static int SIZE = 1;
    protected final static int RATIO = 2;
    protected Image result;
    protected int newWidth;
    protected int newHeight;
    protected double ratio;
    protected int command;
    {
        minimumArguments = 5;
    }
    
    @Override
    public void initialize(String[] params) throws TooLessArguments, BadArgument {
        super.initialize(params);
        switch (params[3]) {
            case "size":
                if (params.length < minimumArguments+1) {
                    throw new TooLessArguments();
                }
                setArguments(params[1], params[2], Integer.parseInt(params[4]), Integer.parseInt(params[5]));
                break;
            case "ratio":
                setArguments(params[1], params[2], Double.parseDouble(params[4]));
                break;
            default:
                throw new BadArgument(params[3]);
        }
    }
    
    public void setArguments(String readFile, String writeFile, int newWidth, int newHeight) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.command = SIZE;
    }
    
    public void setArguments(String readFile, String writeFile, double ratio) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.ratio = ratio;
        this.command = RATIO;
    }
    
    @Override
    public void loadImage() throws FileNotFound, IOException {
        super.loadImage();
        result = new Image();
        if (this.command == RATIO) {
            this.newWidth = (int)(source.getWidth()*ratio);
            this.newHeight = (int)(source.getHeight()*ratio);
        }
        result.create(newWidth, newHeight);
    }
    
    @Override
    public void saveResults() throws IOException {
        String[] parts = writeFile.split("\\.");
        result.save(writeFile, parts[parts.length-1]);
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\n"
                + "graphed resize <sourcefile> <destfile> size <newwidth> <newheight>\n"
                + "or:\n"
                + "graphed resize <sourcefile> <destfile> ratio <scale>");
    }
    
    @Override
    protected void eachEveryPixel() {
        double ratiox = ((double)source.getWidth())/(newWidth);
        double ratioy = ((double)source.getHeight())/(newHeight);
        for(int x = 0; x < result.getWidth(); ++x) {
            for( int y = 0; y < result.getHeight(); ++y) {
                result.setRGB(x, y, operationForPixel((int)(x * ratiox), (int)(y * ratioy)));
            }
        }
    }
    
    protected int[] operationForPixel(double x, double y) {
        int x0,y0,x1,y1,x2,y2;
        int[] rgb = new int[3];
        double dx,dy;
 
        x1 = (int)x;
        y1 = (int)y;
        dx = (x-x1)*0.5;
        dy = (y-y1)*0.5;
        if (x1 - 1 >= 0) {
            x0 = x1 - 1;
        } else {
            x0 = x1;
        }
        if (y1 - 1 >= 0) {
            y0 = y1 - 1;
        } else {
            y0 = y1;
        }
        if (x1 + 1 >= source.getWidth()) {
            x2 = x1;
        } else {
            x2 = x1 + 1;
        }
        if (y1 + 1 >= source.getHeight()) {
            y2 = y1;
        } else {
            y2 = y1 + 1;
        }
 
        rgb[0] = (int)normInterpolation(interpolation(source.getR(x0, y0), source.getR(x1, y0), source.getR(x2, y0), dx),
                                    interpolation(source.getR(x0, y1), source.getR(x1, y1), source.getR(x2, y1), dx),
                                    interpolation(source.getR(x0, y2), source.getR(x1, y2), source.getR(x2, y2), dx), dy);
        rgb[1] = (int)normInterpolation(interpolation(source.getG(x0, y0), source.getG(x1, y0), source.getG(x2, y0), dx),
                                    interpolation(source.getG(x0, y1), source.getG(x1, y1), source.getG(x2, y1), dx),
                                    interpolation(source.getG(x0, y2), source.getG(x1, y2), source.getG(x2, y2), dx), dy);
        rgb[2] = (int)normInterpolation(interpolation(source.getB(x0, y0), source.getB(x1, y0), source.getB(x2, y0), dx),
                                    interpolation(source.getB(x0, y1), source.getB(x1, y1), source.getB(x2, y1), dx),
                                    interpolation(source.getB(x0, y2), source.getB(x1, y2), source.getB(x2, y2), dx), dy);
        return rgb;
    }
    
    private double interpolation(double f1, double f2, double f3, double d)
    {
        return (f2 + (f3 - f1)*d + (f1 - 2*f2 + f3)*d*d);
    }
 
    private double normInterpolation(double f1, double f2, double f3, double d)
    {
        double tmpResult = interpolation(f1, f2, f3, d);
        if (tmpResult > 255) return 255;
        if (tmpResult < 0) return 0;
        return tmpResult;
    }
}
