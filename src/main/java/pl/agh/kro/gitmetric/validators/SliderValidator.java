/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.agh.kro.gitmetric.validators;

/**
 *
 * @author Tomek
 */
public class SliderValidator {
    public void validate(javax.swing.JSlider min,javax.swing.JSlider max,int maxValue){
        min.setValue(0);
        max.setValue(0);
        min.setMaximum(maxValue-1);
        max.setMaximum(maxValue-1);
        min.setValue(0);
        max.setValue(maxValue-1);
    }
}
