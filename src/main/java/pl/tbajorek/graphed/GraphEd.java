/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed;

import java.io.IOException;
import pl.tbajorek.graphed.exception.BadArgument;
import pl.tbajorek.graphed.exception.FileNotFound;
import pl.tbajorek.graphed.exception.ModuleNotFound;
import pl.tbajorek.graphed.modules.ModuleInterface;
import pl.tbajorek.graphed.exception.TooLessArguments;

/**
 *
 * @author tomek
 */
public class GraphEd {
    public static void main(String[] args) {
        String modname = "";
        try {
            ModuleInterface module = Loader.instantiate(args);
            module.initialize(args);
            module.loadImage();
            module.execute();
            module.saveResults();
            if(module.checkIfDone()) {
                System.out.println("Zrobione.");
            }
        } catch(TooLessArguments e) {
            System.out.println("Podano zbyt malo argumentow. Sprobuj ponownie.");
        } catch(BadArgument e) {
            System.out.println("Podany argument jest nieprawidlowy: "+e.getMessage());
        } catch(ModuleNotFound e) {
            System.out.println("Nie znaleziono zadanego modulu: "+e.getMessage());
        } catch(IllegalStateException e) {
            System.out.println("Problem wewnetrzny aplikacji: "+e.getMessage());
        } catch(FileNotFound e) {
            System.out.println("Nie znaleziono pliku: "+e.getMessage());
        } catch(IOException  e) {
            System.out.println("Wystapil blad wejscia/wyjscia");
        }
    }
    
    
}
