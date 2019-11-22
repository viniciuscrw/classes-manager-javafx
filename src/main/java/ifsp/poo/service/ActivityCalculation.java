package ifsp.poo.service;

public class ActivityCalculation implements CalculationStrategy {

    private final int WEIGHT = 3;

    @Override
    public Double calculate(Double value) {
        return value * WEIGHT;
    }

    @Override
    public int getWeight() {
        return WEIGHT;
    }
}
