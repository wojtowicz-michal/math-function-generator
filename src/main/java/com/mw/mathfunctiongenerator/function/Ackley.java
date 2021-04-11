package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.*;

public class Ackley implements Formula {
    public static final String FORMULA = "-20 * exp(-0.2 * sqrt((x ** 2 + y ** 2) / 2)) " +
            "- exp((cos(2 * pi * x) + cos(2 * pi * y)) / 2) + 20 + exp(1)";

    @Override
    public double calculate(List<Double> args) {
        int a = 20;
        double b = 0.2;
        double c = 2 * PI;
        double sum1 = 0;
        double sum2 = 0;
        for (Double d : args) {
            sum1 += d * d;
            sum2 += cos(c * d);
        }
        sum1 = -a * exp(-b * sqrt(sum1 / args.size()));
        sum2 = -exp(sum2 / args.size());
        return sum1 + sum2 + a + exp(1);
    }

    @Override
    public double calculate(double x, double y) {
        return -20 * exp(-0.2 * sqrt((x * x + y * y) / 2)) - exp((cos(2 * PI * x) + cos(2 * PI * y)) / 2) + 20 + exp(1);
    }
}
