/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

import java.awt.image.BufferedImage;

/**
 *
 * @author tomek
 */
public class Recolorize extends MainModule {
    protected int accent;
    protected int range;
    protected int newValue;
    {
        minimumArguments = 6;
    }
    
    @Override
    public void initialize(String[] params) {
        setArguments(params[1], params[2], Integer.parseInt(params[3]), Integer.parseInt(params[4]), Integer.parseInt(params[5]));
    }
    
    public void setArguments(String readFile, String writeFile, int accent, int range, int newValue) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        this.accent = accent;
        this.range = range;
        this.newValue = newValue;
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
                if(((hfrom <= hto && hfrom <= hsv[0] && hto >= hsv[0]) ||
                    (hfrom > hto && (hfrom <= hsv[0] || hto >= hsv[0])))) {
                    hsv[0] = hsv[0]+newValue;
                    if (Math.abs(hsv[0]) >= 360) {
                        hsv[0] -= hsv[0]*(int)(hsv[0]/360.0);
                    } else if(hsv[0] < 0) {
                        hsv[0] = 360.0 + hsv[0];
                    }
                    source.setHSV(x, y, hsv);
                }
            }
        }
    }

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed exposure <sourcefile> <destfile> <accent> <range> <newvalue>");
    }
    
}