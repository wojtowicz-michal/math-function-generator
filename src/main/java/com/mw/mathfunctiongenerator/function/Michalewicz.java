package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static java.lang.Math.*;

public class Michalewicz implements Formula {
    public static final String FORMULA = "-(sin(x) * (sin(1 * x ** 2/pi)) ** 20 " +
            "+ sin(y) * (sin(2 * y ** 2/pi)) ** 20)";

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
        return -(sin(x) * pow(sin(pow((1 * x), 2) / PI), 20) + sin(y) * pow((sin(pow((2 * y), 2) / PI)), 20));
    }
}
