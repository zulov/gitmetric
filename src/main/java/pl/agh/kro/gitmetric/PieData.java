/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric;

/**
 *
 * @author Tomek
 */
public class PieData {
    String name;
    int value;

    public PieData(String name, int lines) {
        this.name = name;
        this.value = lines;
    }
    
}
