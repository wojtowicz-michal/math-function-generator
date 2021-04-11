package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.*;

public class Schwefel implements Formula {
    public static final String FORMULA = "418.9829 * 2 " +
            "- (x * sin(sqrt(abs(x))) + y * sin(sqrt(abs(y))))";

    @Override
    public double calculate(List<Double> args) {
        double sum = 0;
        for (Double d : args) {
            sum += d * sin(sqrt(abs(d)));
        }
        sum = 418.9829 * args.size() - sum;
        return sum;
    }

    @Override
    public double calculate(double x, double y) {
        return 418.9829 * 2 - (x * sin(sqrt(abs(x))) + y * sin(sqrt(abs(y))));
    }
}
