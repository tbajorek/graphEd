/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

import pl.tbajorek.graphed.exception.TooLessArguments;
import java.io.IOException;
import pl.tbajorek.graphed.exception.BadArgument;
import pl.tbajorek.graphed.exception.FileNotFound;
import pl.tbajorek.graphed.exception.ModuleNotFound;
import pl.tbajorek.graphed.model.Image;

/**
 *
 * @author tomek
 */
public interface ModuleInterface {
    public void initialize(String[] params) throws TooLessArguments, BadArgument;
    public boolean checkParams(String[] params) throws TooLessArguments, BadArgument;
    public void loadImage() throws FileNotFound, IOException;
    public boolean execute() throws ModuleNotFound;
    public void saveResults() throws IOException;
    public void help();
    public Image getImage();
}
