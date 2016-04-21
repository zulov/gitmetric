package pl.agh.kro.gitmetric.marking;

import java.util.Map;

/**
 * @author Tomek
 */
public class BasicMarking extends Marking {

    public BasicMarking() {
        super();
    }
    
    @Override
    public void incMap(String person,Integer value,String line) {
        if(users.containsKey(person)){
            users.put(person,users.get(person)+value);
        }else{
            users.put(person,value);
        }
    }
    
}
