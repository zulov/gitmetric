
package pl.agh.kro.gitmetric.marking;

/**
 * @author Tomek
 */
public class MarkingFactory {
    public static Marking getMarking(String name){
        if(name.equals("Linie kodu")){
            return new BasicMarking();
        }else if(name.equals("Bez pustych")){
            return new WithoutEmptyMarking();
        }else if(name.equals("Dlugosc")){
            return new LengthMarking();
        }else{
            return new BasicMarking();
        }
    }
}
