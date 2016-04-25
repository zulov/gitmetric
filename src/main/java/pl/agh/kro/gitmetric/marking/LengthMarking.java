package pl.agh.kro.gitmetric.marking;

import java.util.Date;

/**
 * @author Tomek
 */
public class LengthMarking extends BasicMarking {

    @Override
    public void incMap(String person, Integer value, String line, Date date) {
        super.incMap(person, value * line.length(), line, date);
    }

}
