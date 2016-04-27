package pl.agh.kro.gitmetric.marking;

import java.util.Calendar;
import java.util.Date;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

/**
 * @author Tomek
 */
public class BasicMarking extends Marking {

    public BasicMarking() {
        super();
    }

    @Override
    public boolean incMap(String person, Integer value, String line, Date date) {
        if (users.containsKey(person)) {
            users.put(person, users.get(person) + value);
        } else {
            users.put(person, value);
        }
        Day day = new Day(date);
        if (userToHistory.containsKey(person)) {
            TimeSeries ts = userToHistory.get(person);
            Number currentValue = ts.getValue(day);
            if (currentValue == null) {
                currentValue = 0;
            }
            ts.addOrUpdate(day, value + currentValue.intValue());
        } else {
            TimeSeries ts = new TimeSeries(person);
            userToHistory.put(person, ts);
        }
        return true;
    }
}
