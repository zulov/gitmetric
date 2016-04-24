package pl.agh.kro.gitmetric.validators;

import javax.swing.JSlider;

/**
 * @author Tomek
 */
public class SliderValidator {

    public void validate(JSlider min, JSlider max, int maxValue) {
        if (min.getValue() >= maxValue - 1) {
            min.setValue(0);
        }

        if (max.getValue() > maxValue - 1) {
            max.setValue(maxValue - 1);
        }
        min.setMaximum(maxValue - 1);
        max.setMaximum(maxValue - 1);
    }
}
