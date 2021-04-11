package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class Bohachevsky2 implements Formula {
    public static final String FORMULA = "x * x + 2 * x * x - 0.3 " +
            "* cos(3 * pi * x) * cos(4 * pi * x) + 0.3";

    @Override
    public double calculate(List<Double> args) {
        validateArgsNumber(args);
        double x = args.get(0);
        double y = args.get(1);
        return calculate(x, y);
    }

    @Override
    public double calculate(double x, double y) {
        return x * x + 2 * x * x - 0.3 * cos(3 * PI * x) * cos(4 * PI * x) + 0.3;
    }
}
