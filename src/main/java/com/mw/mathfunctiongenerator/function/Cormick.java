package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.*;

public class Cormick implements Formula {
    public static final String FORMULA = "sin(x + y) + ((x - y) ** 2) - 1.5 * x + 2.5 * y + 1";

    @Override
    public double calculate(List<Double> args) {
        double sum = 0;
        int m = 10;
        for (int i = 0; i < args.size(); i++) {
            double d = args.get(i);
            sum += sin(d) * pow((sin((i + 1) * d * d / PI)), (2 * m));
        }
        return -sum;
    }

    @Override
    public double calculate(double x, double y) {
        return sin(x + y) + (pow((x - y), 2)) - 1.5 * x + 2.5 * y + 1;
    }
}
