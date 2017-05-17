/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed;

import pl.tbajorek.graphed.exception.ModuleNotFound;
import pl.tbajorek.graphed.exception.TooLessArguments;
import pl.tbajorek.graphed.modules.MainModule;

/**
 *
 * @author tomek
 */
public class Loader {
    public static MainModule instantiate(final String[] args) throws TooLessArguments, ModuleNotFound {
        return instantiate(getModuleName(args));
    }
    
    public static MainModule instantiate(final String className) throws IllegalStateException, ModuleNotFound {
        try{
            return MainModule.class.cast(Class.forName(className).newInstance());
        } catch(ClassNotFoundException e) {
            throw new ModuleNotFound(className);
        } catch(InstantiationException
              | IllegalAccessException e){
            throw new IllegalStateException(e);
        }
    }
    
    public static String getModuleName(String[] args) throws TooLessArguments {
        if (args.length < 1) {
            throw new TooLessArguments();
        }
        return module2class(args[0]);
    }
    
    public static String module2class(String module) {
        String className = module.substring(0, 1).toUpperCase() + module.substring(1);
        return "pl.tbajorek.graphed.modules."+className;
    }
}
