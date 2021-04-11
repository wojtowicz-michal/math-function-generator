package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.*;

public class Rastrigin implements Formula {
    public static final String FORMULA = "20 + (x ** 2 - 10 * cos(2 * pi * x) " +
            "+ y ** 2 - 10 * cos(2 * pi * y))";

    @Override
    public double calculate(List<Double> args) {
        double sum = 0;
        for (Double d : args) {
            sum += d * d - 10 * cos(2 * PI * d);
        }
        sum += 10 * args.size();
        return sum;
    }

    @Override
    public double calculate(double x, double y) {
        return 20 + (pow(x, 2) - 10 * cos(2 * Math.PI * x) + pow(y, 2) - 10 * cos(2 * PI * y));
    }
}
