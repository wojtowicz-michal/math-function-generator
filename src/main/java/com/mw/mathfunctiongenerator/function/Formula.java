package com.mw.mathfunctiongenerator.function;

import java.util.List;

import static com.mw.mathfunctiongenerator.controller.RootController.showAlert;
import static com.mw.mathfunctiongenerator.util.Constants.ARGS_LIMIT;

public interface Formula {
    double calculate(double x, double y);
    double calculate(List<Double> args);

    default void validateArgsNumber(List<Double> args) {
        if (args.size() > 2) {
            showAlert(ARGS_LIMIT);
            args.remove(args.size() - 1);
            calculate(args);
        }
    }
}
