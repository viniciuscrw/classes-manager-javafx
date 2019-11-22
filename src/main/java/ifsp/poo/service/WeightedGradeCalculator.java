package ifsp.poo.service;

public class WeightedGradeCalculator {

    public Double calculateWeighted(Double gradeValue, CalculationStrategy calculationStrategy) {
        return calculationStrategy.calculate(gradeValue);
    }

    public int getGradeWeight(CalculationStrategy calculationStrategy) {
        return calculationStrategy.getWeight();
    }
}
