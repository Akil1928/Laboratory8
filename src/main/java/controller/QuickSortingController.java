package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.IntegerStringConverter;
import domain.Complex;
import util.FXUtility;
import util.Utility;

import java.util.Arrays;

public class QuickSortingController {

    @FXML
    private Button ClearButton;

    @FXML
    private Button CreateButton;

    @FXML
    private Button RamdomizeButton;

    @FXML
    private TableView<Integer[]> SortedArrayableView1;

    @FXML
    private Button StartButton;

    @FXML
    private TextField TextFieldArrayLength;

    @FXML
    private TextField TextFieldHghBound;

    @FXML
    private TextField TextFieldHigh;

    @FXML
    private TextField TextFieldLow;

    @FXML
    private TextField TextFieldLowBound;

    @FXML
    private TextField TextFieldPivot;

    @FXML
    private TextField TextFieldRecursiveCalls;

    @FXML
    private TableView<Integer[]> noSortedArrayableView;

    private int[] array;
    private Complex complex;

    @FXML
    public void initialize() {
        StartButton.setDisable(true);
        RamdomizeButton.setDisable(true);
        ClearButton.setDisable(true);
        complex = new Complex();

        TextFieldArrayLength.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            if (change.getControlNewText().isEmpty() || change.getControlNewText().matches("-?\\d*")) {
                return change;
            } else {
                return null;
            }
        }));

        TextFieldLowBound.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            if (change.getControlNewText().isEmpty() || change.getControlNewText().matches("-?\\d*")) {
                return change;
            } else {
                return null;
            }
        }));

        TextFieldHghBound.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            if (change.getControlNewText().isEmpty() || change.getControlNewText().matches("-?\\d*")) {
                return change;
            } else {
                return null;
            }
        }));
    }

    private void actualizarTableView(TableView<Integer[]> tableView, int[] arr) {
        tableView.getColumns().clear();
        tableView.getItems().clear();

        for (int i = 0; i < arr.length; i++) {
            final int columnIndex = i;
            TableColumn<Integer[], Integer> column = new TableColumn<>(String.valueOf(i));
            column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()[columnIndex]));
            column.setPrefWidth(50);
            column.setStyle("-fx-alignment: CENTER;");
            tableView.getColumns().add(column);
        }

        Integer[] valores = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            valores[i] = arr[i];
        }

        ObservableList<Integer[]> rows = FXCollections.observableArrayList();
        rows.add(valores);
        tableView.setItems(rows);
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        TextFieldArrayLength.clear();
        TextFieldLowBound.clear();
        TextFieldHghBound.clear();
        TextFieldLow.clear();
        TextFieldHigh.clear();
        TextFieldPivot.clear();
        TextFieldRecursiveCalls.clear();

        noSortedArrayableView.getItems().clear();
        noSortedArrayableView.getColumns().clear();
        SortedArrayableView1.getItems().clear();
        SortedArrayableView1.getColumns().clear();

        array = null;
        complex = new Complex();

        StartButton.setDisable(true);
        RamdomizeButton.setDisable(true);
        ClearButton.setDisable(true);
    }

    @FXML
    void createOnAction(ActionEvent event) {
        try {
            if (TextFieldArrayLength.getText().isEmpty() || TextFieldLowBound.getText().isEmpty() || TextFieldHghBound.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, complete todos los campos para crear el array.");
                alert.showAndWait();
                return;
            }

            int length = Integer.parseInt(TextFieldArrayLength.getText());
            int lowBound = Integer.parseInt(TextFieldLowBound.getText());
            int highBound = Integer.parseInt(TextFieldHghBound.getText());

            if (length > 200 || lowBound > highBound) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Ingrese datos correctos para crear.");
                alert.showAndWait();
                return;
            }

            array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = lowBound + Utility.getRandom(highBound - lowBound);
            }

            actualizarTableView(noSortedArrayableView, array);

            StartButton.setDisable(false);
            RamdomizeButton.setDisable(false);
            ClearButton.setDisable(false);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ingrese valores numéricos válidos.");
            alert.showAndWait();
        }
    }

    @FXML
    void randomizeOnAction(ActionEvent event) {
        if (array != null) {
            try {
                int lowBound = Integer.parseInt(TextFieldLowBound.getText());
                int highBound = Integer.parseInt(TextFieldHghBound.getText());

                if (lowBound > highBound) {
                    FXUtility.alert("Error", "El límite inferior no puede ser mayor que el límite superior.").showAndWait();
                    return;
                }

                for (int i = 0; i < array.length; i++) {
                    array[i] = lowBound + Utility.getRandom(highBound - lowBound);
                }

                actualizarTableView(noSortedArrayableView, array);
                SortedArrayableView1.getItems().clear();
                SortedArrayableView1.getColumns().clear();
                TextFieldLow.clear();
                TextFieldHigh.clear();
                TextFieldPivot.clear();
                TextFieldRecursiveCalls.clear();

            } catch (NumberFormatException e) {
                FXUtility.alert("Error", "Por favor ingrese valores numéricos válidos para los límites.").showAndWait();
            }
        }
    }

    @FXML
    void startOnAction(ActionEvent event) {
        if (array != null) {
            int[] arrayOrdenado = Utility.copyArray(array);
            complex.resetCounters();
            complex.quickSort(arrayOrdenado, 0, arrayOrdenado.length - 1);
            actualizarTableView(SortedArrayableView1, arrayOrdenado);
            int[] lowValues = complex.getLowValues();
            int[] highValues = complex.getHighValues();
            int[] pivotValues = complex.getPivotValues();
            Arrays.sort(lowValues);
            StringBuilder lowStr = new StringBuilder();
            for (int value : lowValues) {
                if (lowStr.length() > 0) {
                    lowStr.append(", ");
                }
                lowStr.append(value);
            }
            TextFieldLow.setText(lowStr.toString());
            Arrays.sort(highValues);
            StringBuilder highStr = new StringBuilder();
            for (int i = highValues.length - 1; i >= 0; i--) {
                if (highStr.length() > 0) {
                    highStr.append(", ");
                }
                highStr.append(highValues[i]);
            }
            TextFieldHigh.setText(highStr.toString());
            if (pivotValues.length > 0) {
                StringBuilder pivotStr = new StringBuilder();
                for (int value : pivotValues) {
                    if (pivotStr.length() > 0) {
                        pivotStr.append(", ");
                    }
                    pivotStr.append(value);
                }
                TextFieldPivot.setText(pivotStr.toString());
            }
            TextFieldRecursiveCalls.setText(String.valueOf(complex.getRecursiveCallCount()));
        }
    }
}