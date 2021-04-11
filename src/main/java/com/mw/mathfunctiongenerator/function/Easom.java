package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.*;

public class Easom implements Formula {
    public static final String FORMULA = "-cos(x) * cos(y) " +
            "* exp(-(x - pi) ** 2 - (y - pi) ** 2) + 1";

    @Override
    public double calculate(List<Double> args) {
        validateArgsNumber(args);
        double x = args.get(0);
        double y = args.get(1);
        return calculate(x, y);
    }

    @Override
    public double calculate(double x, double y) {
        return -cos(x) * cos(y) * exp(-pow((x - PI), 2) - pow((y - PI), 2)) + 1;
    }
}
