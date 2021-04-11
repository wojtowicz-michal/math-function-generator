package com.mw.mathfunctiongenerator.function;

import java.util.List;

public class Sphere implements Formula {

    public static final String FORMULA = "x ** 2 + y ** 2";

    @Override
    public double calculate(List<Double> args) {
        double sum = 0;
        for (Double d : args) {
            sum += d * d;
        }
        return sum;
    }

    @Override
    public double calculate(double x, double y) {
        return x * x + y * y;
    }
}
