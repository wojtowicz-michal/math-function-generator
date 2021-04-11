package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.*;

public class Leon implements Formula {
    public static final String FORMULA = "100 * ((y - x**3)**2) + ((1 - x)**2)";

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
        return 100 * (y - pow(pow(x, 3), 2) + pow(1 - x, 2));
    }
}
