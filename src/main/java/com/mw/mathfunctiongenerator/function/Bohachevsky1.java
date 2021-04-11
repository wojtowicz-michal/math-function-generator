package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class Bohachevsky1 implements Formula {
    public static final String FORMULA = "x * x + 2 * y * y - 0.3 " +
            "* cos(3 * pi * x) - 0.4 * cos(4 * pi * y) + 0.7";

    @Override
    public double calculate(List<Double> args) {
        validateArgsNumber(args);
        double x = args.get(0);
        double y = args.get(1);
        return calculate(x, y);
    }

    @Override
    public double calculate(double x, double y) {
        return x * x + 2 * y * y - 0.3 * cos(3 * PI * x) - 0.4 * cos(4 * PI * y) + 0.7;
    }
}
