/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric.marking;

import java.util.Date;

/**
 *
 * @author Tomek
 */
public class WithoutEmptyMarking extends BasicMarking {

    @Override
    public boolean incMap(String person, Integer value, String line, Date date) {
        if (line != null && !line.equals("")) {
            return super.incMap(person, value, line, date);
        }
        return false;
    }

}
