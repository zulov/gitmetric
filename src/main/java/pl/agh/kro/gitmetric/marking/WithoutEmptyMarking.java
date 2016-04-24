/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric.marking;

/**
 *
 * @author Tomek
 */
public class WithoutEmptyMarking extends BasicMarking {

    @Override
    public void incMap(String person, Integer value, String line) {
        if (line != null && !line.equals("")) {
            super.incMap(person, value, line);
        }
    }

}
