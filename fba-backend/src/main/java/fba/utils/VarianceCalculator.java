package fba.utils;

import javafx.util.Pair;

import java.util.List;

public class VarianceCalculator {
    public static Pair<Double, Double> calculateVarianceAndMean(List<Double> values) {
        int n = values.size();
        double sum = 0;
        for (Double aDouble : values) {
            sum += aDouble;
        }
        double mean = sum / n;

        // Calculate the variance
        double variance = 0;
        for (Double value : values) {
            double diff = value - mean;
            variance += diff * diff;
        }
        variance /= n;

        return new Pair<>(variance, mean);
    }

    public static Double calculateMean(List<Double> values) {
        int n = values.size();
        if (n == 0) return 0.0;
        double sum = 0;
        for (Double aDouble : values) {
            sum += aDouble;
        }
        double mean = sum / n;
        return mean;
    }
}
