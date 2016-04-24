package pl.agh.kro.gitmetric.marking;

/**
 * @author Tomek
 */
public class LengthMarking extends BasicMarking{

    @Override
    public void incMap(String person, Integer value, String line) {
        super.incMap(person, value*line.length(), line);
    }
    
}
