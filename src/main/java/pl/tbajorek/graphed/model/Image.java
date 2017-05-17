/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pl.tbajorek.graphed.exception.FileNotFound;

/**
 *
 * @author tomek
 */
public class Image {
    private BufferedImage image;
    
    public void open(String filename) throws FileNotFound, IOException {
        File file = new File(filename);
        if(!file.exists()) {
            throw new FileNotFound(filename);
        }
        image = ImageIO.read(file);
    }
    
    public void create(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    }
    
    public int[] getRGB(int x, int y) {
        int[] rgb = new int[3];
        int color = image.getRGB(x, y);
        rgb[0] = (color >> 16) & 0xff;//red
        rgb[1] = (color >> 8) & 0xff;//green
        rgb[2] = (color) & 0xff;//blue
        return rgb;
    }
    
    public int getR(int x, int y) {
        int color = image.getRGB(x, y);
        return (color >> 16) & 0xff;
    }
    
    public int getG(int x, int y) {
        int color = image.getRGB(x, y);
        return (color >> 8) & 0xff;
    }
    
    public int getB(int x, int y) {
        int color = image.getRGB(x, y);
        return (color) & 0xff;
    }
    
    public double[] getHSV(int x, int y) {
        int[] rgb = getRGB(x, y);
        double hsv[] = new double[3];
        double i, f;
        int min = Math.min(Math.min(rgb[0], rgb[1]), rgb[2]);
        hsv[2] = Math.max(Math.max(rgb[0], rgb[1]), rgb[2]);
        if (min == hsv[2]){
            hsv[0] = 0;
            hsv[1] = 0;
        }
        else {
            f = (rgb[0] == min) ? rgb[1]-rgb[2] : ((rgb[1] == min) ? rgb[2]-rgb[0] : rgb[0]-rgb[1]);
            i = (rgb[0] == min) ? 3 : ((rgb[1] == min) ? 5 : 1);
            hsv[0] = ((i-f/(hsv[2]-min))*60.0)%360.0;
            hsv[1] = (hsv[2]-min)/hsv[2];
        }
        
        return hsv;
    }
    
    public void setRGB(int x, int y, int[] rgb) {
        int color = (int)((((rgb[0] << 8)|rgb[1]) << 8)|rgb[2]);
        image.setRGB(x, y, color);
    }
    
    public void setHSV(int x, int y, double[] hsv) {
        int[] rgb = new int[3];
        int i;
        double f, p, q, t;
        double hue = hsv[0], sat = hsv[1], val=hsv[2];
 
        if(val==0) {
            rgb[0] = 0;
            rgb[1] = 0;
            rgb[2] = 0;
        } else {
            hue /= 60.0;
            i = (int)Math.floor(hue);
            f = hue-i;
            p = val*(1.0-sat);
            q = val*(1.0-(sat*f));
            t = val*(1.0-(sat*(1.0-f)));
            if (i==0) {
                rgb[0]=(int)val;rgb[1]=(int)t; rgb[2]=(int)p;
            } else if (i==1) {
                rgb[0]=(int)q; rgb[1]=(int)val; rgb[2]=(int)p;
            } else if (i==2) {
                rgb[0]=(int)p; rgb[1]=(int)val; rgb[2]=(int)t;
            } else if (i==3) {
                rgb[0]=(int)p; rgb[1]=(int)q; rgb[2]=(int)val;
            } else if (i==4) {
                rgb[0]=(int)t; rgb[1]=(int)p; rgb[2]=(int)val;
            } else if (i==5) {
                rgb[0]=(int)val; rgb[1]=(int)p; rgb[2]=(int)q;
            }
        }
        setRGB(x, y, rgb);
    }
    
    public int getWidth() {
        return image.getWidth();
    }
    
    public int getHeight() {
        return image.getHeight();
    }
    
    public void save(String filename, String extension) throws IOException {
        ImageIO.write(image,extension,new File(filename));
    }
    
    public BufferedImage getOriginal() {
        return image;
    }
    
    public void setOriginal(BufferedImage original) {
        image = original;
    }
}
