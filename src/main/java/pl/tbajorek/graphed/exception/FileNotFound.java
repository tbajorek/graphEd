/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tbajorek.graphed.exception;

/**
 *
 * @author tomek
 */
public class FileNotFound extends Exception {

    public FileNotFound(String filename) {
        super("File not found: "+filename);
    }
    
}
