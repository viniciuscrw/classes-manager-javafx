package ifsp.poo.service;

public class TestCalculation implements CalculationStrategy {

    private final int WEIGHT = 7;

    @Override
    public Double calculate(Double value) {
        return value * WEIGHT;
    }

    @Override
    public int getWeight() {
        return WEIGHT;
    }
}
