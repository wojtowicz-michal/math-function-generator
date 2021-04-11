package com.mw.mathfunctiongenerator.controller;

import com.mw.mathfunctiongenerator.function.*;
import gnuplot.JavaPlot;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.mw.mathfunctiongenerator.util.Constants.*;

public class RootController implements Initializable {
    @FXML
    private TextArea argsList;
    @FXML
    private TextArea functionValue;
    @FXML
    private TextArea functionFormula;

    @FXML
    private Label argsNumber;
    @FXML
    private TextField arg;
    @FXML
    private ChoiceBox<String> function;

    private Formula formula;
    private final List<Double> args = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] functions = {
                LEON,
                EASOM,
                ACKLEY,
                SPHERE,
                CORMICK,
                SCHWEFEL,
                RASTRIGIN,
                MICHALEWICZ,
                BOHACHEVSKY1,
                BOHACHEVSKY2
        };

        function.setItems(FXCollections.observableArrayList(functions));
        function.setOnAction(event -> {
            String name = function.getSelectionModel().getSelectedItem();
            if (name == null) return;
            String formulaText = initializeFormula(name);
            functionFormula.setText(formulaText);
            clearArgs();
        });
    }

    @FXML
    private void addArg() {
        if (formula == null) {
            showAlert(NO_FUNCTION_SELECTED);
        } else {
            String functionArg = arg.getText().replaceAll(WHITESPACE, "");
            if (isArgValid(functionArg)) {
                args.add(Double.parseDouble(functionArg));
                calculateValue();
                updateText();
            } else {
                showAlert(INVALID_ARG);
            }
        }
    }

    @FXML
    private void removeArg() {
        if (args.isEmpty()) {
            showAlert(NO_ARGS_PROVIDED);
        } else {
            args.remove(args.size() - 1);
            calculateValue();
            updateText();
        }
    }

    @FXML
    private void clearArgs() {
        args.clear();
        argsNumber.setText("0");
        argsList.setText("");
        functionValue.setText("");
    }

    @FXML
    private void generatePlot() {
        String functionFormulaText = functionFormula.getText();
        if (functionFormulaText.equals("")) {
            showAlert(NO_FUNCTION_PROVIDED);
        } else {
            JavaPlot plot = new JavaPlot(GNUPLOT_PATH, true);
            plot.addPlot(functionFormulaText);
            plot.plot();
        }
    }

    private void calculateValue() {
        if (formula == null) return;
        functionValue.setText(Double.toString(formula.calculate(args)));
    }

    private void updateText() {
        StringBuilder updatedText = new StringBuilder();
        args.forEach(a -> updatedText.append(a).append("; "));
        argsList.setText(updatedText.toString());
        argsNumber.setText(Integer.toString(args.size()));
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setContentText(message);
        alert.show();
    }

    private boolean isArgValid(String arg) {
        return arg.matches(ARG_REGEX);
    }

    private String initializeFormula(String name) {
        String formulaText;
        switch (name) {
            case LEON:
                formulaText = Leon.FORMULA;
                formula = new Leon();
                break;
            case EASOM:
                formulaText = Easom.FORMULA;
                formula = new Easom();
                break;
            case ACKLEY:
                formulaText = Ackley.FORMULA;
                formula = new Ackley();
                break;
            case SPHERE:
                formulaText = Sphere.FORMULA;
                formula = new Sphere();
                break;
            case CORMICK:
                formulaText = Cormick.FORMULA;
                formula = new Cormick();
                break;
            case SCHWEFEL:
                formulaText = Schwefel.FORMULA;
                formula = new Schwefel();
                break;
            case RASTRIGIN:
                formulaText = Rastrigin.FORMULA;
                formula = new Rastrigin();
                break;
            case MICHALEWICZ:
                formulaText = Michalewicz.FORMULA;
                formula = new Michalewicz();
                break;
            case BOHACHEVSKY1:
                formulaText = Bohachevsky1.FORMULA;
                formula = new Bohachevsky1();
                break;
            case BOHACHEVSKY2:
                formulaText = Bohachevsky2.FORMULA;
                formula = new Bohachevsky2();
                break;
            default:
                throw new IllegalArgumentException();
        }
        return formulaText;
    }
}
