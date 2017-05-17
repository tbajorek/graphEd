/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.modules;

import java.io.IOException;
import pl.tbajorek.graphed.Loader;
import pl.tbajorek.graphed.exception.FileNotFound;
import pl.tbajorek.graphed.exception.ModuleNotFound;
import pl.tbajorek.graphed.exception.TooLessArguments;
import pl.tbajorek.graphed.model.Image;

/**
 *
 * @author tomek
 */
public class Help extends MainModule {
    private String command;
    {
        minimumArguments = 2;
    }
    @Override
    public void initialize(String[] params) throws TooLessArguments {
        if (params.length < minimumArguments) {
            throw new TooLessArguments();
        }
        setArguments(params[1]);
    }
    
    public void setArguments(String command) {
        this.command = command;
    }

    @Override
    public void loadImage() throws FileNotFound, IOException {}

    @Override
    public boolean execute() throws ModuleNotFound, IllegalStateException {
        MainModule module = Loader.instantiate(Loader.module2class(command));
        module.help();
        return true;
    }

    @Override
    public void saveResults() throws IOException {}

    @Override
    public void help() {
        System.out.println("Please run the program:\ngraphed grayscale <sourcefile> <destfile>");
    }

    @Override
    public Image getImage() {
        return new Image();
    }
    
}
