package controller;

import domain.NumberItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class SelectionSortingController {

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
    private TextField TextFieldHghBound;

    @FXML
    private TextField TextFieldLowBound;

    @FXML
    private TextField TextFieldMinIndex;

    @FXML
    private TextField TextFieldMinValue;

    @FXML
    private TextField TextFieldtotalChanges;

    @FXML
    private TextField TextFieldtotalIterations;

    @FXML
    private TableView<NumberItem> noSortedArrayableView;

    @FXML
    private ScrollPane noSortedScrollPane;

    @FXML
    private ScrollPane sortedScrollPane;

    private ObservableList<NumberItem> originalList = FXCollections.observableArrayList();
    private ObservableList<NumberItem> sortedList = FXCollections.observableArrayList();
    private List<Integer> currentArray = new ArrayList<>(); // Para guardar el array actual

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
                showAlert("Valores inválidos: debe estar entre 1 y 200, y el límite inferior no puede ser mayor que el límite superior.");
                return;
            }

            currentArray.clear();
            Random rand = new Random();
            for (int i = 0; i < size; i++) {
                currentArray.add(rand.nextInt(high - low + 1) + low);
            }
            updateTableView(noSortedArrayableView, currentArray);
            SortedArrayableView1.getItems().clear();
            SortedArrayableView1.getColumns().clear();
            TextFieldMinValue.clear();
            TextFieldMinIndex.clear();
            TextFieldtotalIterations.clear();
            TextFieldtotalChanges.clear();

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
        if (currentArray.isEmpty()) {
            showAlert("Debe crear primero el arreglo.");
            return;
        }

        List<Integer> arrayToSort = new ArrayList<>(currentArray);
        int n = arrayToSort.size();
        int iterations = 0;
        int changes = 0;

        // Inicializar cadenas para mostrar la historia de min value y min index
        StringBuilder minValueHistory = new StringBuilder();
        StringBuilder minIndexHistory = new StringBuilder();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            int minValue = arrayToSort.get(i);
            for (int j = i + 1; j < n; j++) {
                iterations++;
                if (arrayToSort.get(j) < minValue) {
                    minValue = arrayToSort.get(j);
                    minIndex = j;
                }
            }
            // Append los valores actuales a la historia
            minValueHistory.append(minValue).append(" ");
            minIndexHistory.append(minIndex).append(" ");

            if (minIndex != i) {
                int temp = arrayToSort.get(i);
                arrayToSort.set(i, arrayToSort.get(minIndex));
                arrayToSort.set(minIndex, temp);
                changes++;
            }
        }

        updateTableView(SortedArrayableView1, arrayToSort);
        TextFieldMinValue.setText(minValueHistory.toString().trim()); // Mostrar la historia completa
        TextFieldMinIndex.setText(minIndexHistory.toString().trim()); // Mostrar la historia completa
        TextFieldtotalIterations.setText(String.valueOf(iterations));
        TextFieldtotalChanges.setText(String.valueOf(changes));
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        currentArray.clear();
        noSortedArrayableView.getItems().clear();
        noSortedArrayableView.getColumns().clear();
        SortedArrayableView1.getItems().clear();
        SortedArrayableView1.getColumns().clear();
        TextFieldArrayLength.clear();
        TextFieldLowBound.clear();
        TextFieldHghBound.clear();
        TextFieldMinValue.clear();
        TextFieldMinIndex.clear();
        TextFieldtotalIterations.clear();
        TextFieldtotalChanges.clear();
    }

    private void updateTableView(TableView<NumberItem> tableView, List<Integer> data) {
        tableView.getColumns().clear();
        if (!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                final int columnIndex = i;
                TableColumn<NumberItem, Integer> column = new TableColumn<>(String.valueOf(i));
                column.setCellValueFactory(cellData -> {
                    if (columnIndex < cellData.getValue().getList().size()) {
                        return new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getList().get(columnIndex)).asObject();
                    } else {
                        return null;
                    }
                });
                tableView.getColumns().add(column);
            }
            tableView.setItems(FXCollections.observableArrayList(new NumberItem(new ArrayList<>(data))));
        } else {
            tableView.setItems(FXCollections.emptyObservableList());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}