import Expectation;
import Characteristics;

public class Expectations {
    private Orientation orientation;
    private Expectation<Int> sociability;
    private Expectation<Int> seriosity;
    private Expectation<Int> sportivity;
    private Expectation<Int> culture;

    public Expectations() {
        //  Faut les construire
    }

    private double calculateScore(Expectation<Int> exp, int value) {
        switch(exp.getFunction()) {
            case CONSTANT: 
                return 0;
            case LINEAR: 
                return Math.abs(exp.getValue() - value);
            case EXPONENTIAL: 
                return Math.exp(Math.abs(exp.getValue() - value) / 2) - 1;
        }
    }


    public double calculateScore(Characteriscs OwnCharac, Characteriscs OtherCharac) {
        double cost = 0;
        switch(orientation) {
            case HOMO:
                if(OwnCharac.getGenre() != OtherCharac.getGenre())
                    cost += 100;
                break;
            case BISEXUAL:
                break;
            case STRAIGHT:
                if(OwnCharac.getGenre() == OtherCharac.getGenre())
                    cost += 100;
                break;
        }

        cost += calculateScore(sociability, OtherCharac.getSociability());
        cost += calculateScore(seriosity, OtherCharac.getSyeriosity());
        cost += calculateScore(sportivity, OtherCharac.getSportivity());
        cost += calculateScore(culture, OtherCharac.getCulture());

        return cost;
    }
}