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
    public void incMap(String person, Integer value, String line, Date date) {
        if (users.containsKey(person)) {
            users.put(person, users.get(person) + value);
        } else {
            users.put(person, value);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Day day = new Day(cal.get(Calendar.DAY_OF_MONTH) + 1, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
        if (userToHistory.containsKey(person)) {
            TimeSeries ts = userToHistory.get(person);
            Number currentValue = ts.getValue(day);
            if (currentValue == null) {
                currentValue = 0;
            }
            ts.addOrUpdate(day, value + currentValue.intValue());
        } else {
            TimeSeries ts = new TimeSeries(person, Day.class);
            //ts.add(new Day(10, 1, 2004), value);
            userToHistory.put(person, ts);
        }

    }
}
