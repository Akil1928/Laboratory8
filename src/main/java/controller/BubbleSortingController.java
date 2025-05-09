package controller;

import domain.NumberItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class BubbleSortingController {

    @FXML
    private Button ClearButton;

    @FXML
    private Button CreateButton;

    @FXML
    private Button RamdomizeButton;

    @FXML
    private TableView<NumberItem> SortedArrayableView1;

    @FXML
    private Button StartButton;

    @FXML
    private TextField TextFieldArrayLength;

    @FXML
    private TextField TextFieldChanges;

    @FXML
    private TextField TextFieldHghBound;

    @FXML
    private TextField TextFieldLowBound;

    @FXML
    private TextField TextFielditerations;

    @FXML
    private TableView<NumberItem> noSortedArrayableView;

    private ObservableList<NumberItem> originalList = FXCollections.observableArrayList();
    private ObservableList<NumberItem> sortedList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar TextFormatter para TextFieldArrayLength
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        TextFieldArrayLength.setTextFormatter(new TextFormatter<>(integerFilter));
        TextFieldLowBound.setTextFormatter(new TextFormatter<>(integerFilter));
        TextFieldHghBound.setTextFormatter(new TextFormatter<>(integerFilter));
    }

    @FXML
    void createOnActio(ActionEvent event) {
        try {
            int size = Integer.parseInt(TextFieldArrayLength.getText());
            int low = Integer.parseInt(TextFieldLowBound.getText());
            int high = Integer.parseInt(TextFieldHghBound.getText());

            if (size > 200 || size <= 0 || low > high) {
                showAlert("Valores inválidos: La longitud del arreglo debe estar entre 1 y 200, y el límite inferior no puede ser mayor que el límite superior.");
                return;
            }

            originalList.clear();
            Random rand = new Random();

            for (int i = 0; i < size; i++) {
                originalList.add(new NumberItem(rand.nextInt(high - low + 1) + low));
            }

            // Configurar las columnas del TableView para el array no ordenado
            noSortedArrayableView.getColumns().clear();
            for (int i = 0; i < size; i++) {
                final int columnIndex = i;
                TableColumn<NumberItem, Integer> column = new TableColumn<>(String.valueOf(i)); // El encabezado es el índice
                column.setCellValueFactory(cellData -> {
                    if (columnIndex < cellData.getValue().getList().size()) {
                        return new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getList().get(columnIndex)).asObject();
                    } else {
                        return null; // Evitar errores si el tamaño de la lista cambia inesperadamente
                    }
                });
                noSortedArrayableView.getColumns().add(column);
            }
            noSortedArrayableView.setItems(FXCollections.observableArrayList(new NumberItem(new ArrayList<>(originalList.stream().map(NumberItem::getValue).toList()))));

            sortedList.clear();
            SortedArrayableView1.getColumns().clear();
            SortedArrayableView1.setItems(null);
            TextFieldChanges.clear();
            TextFielditerations.clear();

        } catch (NumberFormatException e) {
            showAlert("Ingrese valores numéricos válidos para la longitud y los límites del arreglo.");
        }
    }

    @FXML
    void randomizeOnAction(ActionEvent event) {
        createOnActio(event);
    }

    @FXML
    void startOnAction(ActionEvent event) {
        if (originalList.isEmpty()) {
            showAlert("Debe crear primero el arreglo.");
            return;
        }

        List<NumberItem> dataToSort = new ArrayList<>(originalList); // Crear una copia para no modificar la original

        int iterations = 0;
        int changes = 0;

        for (int i = 0; i < dataToSort.size() - 1; i++) {
            for (int j = 0; j < dataToSort.size() - i - 1; j++) {
                iterations++;
                if (dataToSort.get(j).getValue() > dataToSort.get(j + 1).getValue()) {
                    NumberItem temp = dataToSort.get(j);
                    dataToSort.set(j, dataToSort.get(j + 1));
                    dataToSort.set(j + 1, temp);
                    changes++;
                }
            }
        }

        sortedList.setAll(dataToSort);

        // Configurar las columnas del TableView para el array ordenado
        SortedArrayableView1.getColumns().clear();
        int size = sortedList.size();
        for (int i = 0; i < size; i++) {
            final int columnIndex = i;
            TableColumn<NumberItem, Integer> column = new TableColumn<>(String.valueOf(i)); // El encabezado es el índice
            column.setCellValueFactory(cellData -> {
                if (columnIndex < cellData.getValue().getList().size()) {
                    return new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getList().get(columnIndex)).asObject();
                } else {
                    return null;
                }
            });
            SortedArrayableView1.getColumns().add(column);
        }
        SortedArrayableView1.setItems(FXCollections.observableArrayList(new NumberItem(new ArrayList<>(sortedList.stream().map(NumberItem::getValue).toList()))));

        TextFielditerations.setText(String.valueOf(iterations));
        TextFieldChanges.setText(String.valueOf(changes));
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        originalList.clear();
        sortedList.clear();
        noSortedArrayableView.getColumns().clear();
        noSortedArrayableView.setItems(null);
        SortedArrayableView1.getColumns().clear();
        SortedArrayableView1.setItems(null);
        TextFieldArrayLength.clear();
        TextFieldLowBound.clear();
        TextFieldHghBound.clear();
        TextFielditerations.clear();
        TextFieldChanges.clear();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

