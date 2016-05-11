package pl.agh.kro.gitmetric.validators;

import javax.swing.JSpinner;

/**
 * @author Tomek
 */
public class SpinnerValidator {
        public void validate(JSpinner  min, JSpinner max) {
        int minValue =(int)min.getValue();
        int maxValue =(int)max.getValue();
        
        if(minValue>=maxValue){
            maxValue=minValue+1;
        }
        min.setValue(minValue);
        max.setValue(maxValue);
        
    }
}
