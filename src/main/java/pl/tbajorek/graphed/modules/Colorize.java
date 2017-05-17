/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

import pl.tbajorek.graphed.modules.Grayscale;

/**
 *
 * @author tomek
 */
public class Colorize extends MainModule {
    protected int accent;
    protected int range;
    {
        minimumArguments = 5;
    }
    
    @Override
    public void initialize(String[] params) {
        setArguments(params[1], params[2], Integer.parseInt(params[3]), Integer.parseInt(params[4]));
    }
    
    public void setArguments(String readFile, String writeFile, int accent, int range) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.accent = accent;
        this.range = range;
    }
    
    @Override
    public boolean execute() {
        eachEveryPixel();
        return true;
    }
    
    @Override
    protected void eachEveryPixel() {
        int hfrom = (int)((accent - (range/2.0) + 360.0) % 360);
        int hto = (int)((accent + (range/2.0) + 360.0) % 360);
        for(int x = 0; x < source.getWidth(); ++x) {
            for( int y = 0; y < source.getHeight(); ++y) {
                double[] hsv = source.getHSV(x, y);
                if(!((hfrom <= hto && hfrom <= hsv[0] && hto >= hsv[0]) ||
                    (hfrom > hto && (hfrom <= hsv[0] || hto >= hsv[0])))) {
                    source.setRGB(x, y, Grayscale.getPixelInGrayscale(source.getRGB(x, y)));
                }
            }
        }
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed exposure <sourcefile> <destfile> <accent> <range>");
    }
    
}