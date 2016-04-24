package pl.agh.kro.gitmetric.marking;

/**
 * @author Tomek
 */
public class MarkingFactory {

    public static Marking getMarking(String name) {
        switch (name) {
            case "Linie kodu":
                return new BasicMarking();
            case "Bez pustych":
                return new WithoutEmptyMarking();
            case "Dlugosc":
                return new LengthMarking();
            default:
                return new BasicMarking();
        }
    }
}
