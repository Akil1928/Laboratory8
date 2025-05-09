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
import java.util.stream.Collectors;

public class ShellSortingController {

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
    private TextField TextFieldGapSubArray1;

    @FXML
    private TextField TextFieldGapSubArray2;

    @FXML
    private TextField TextFieldGapSubArray3;

    @FXML
    private TextField TextFieldGapn2;

    @FXML
    private TextField TextFieldHghBound;

    @FXML
    private TextField TextFieldLowBound;

    @FXML
    private TableView<NumberItem> noSortedArrayableView;

    @FXML
    private ScrollPane noSortedScrollPane;

    @FXML
    private ScrollPane sortedScrollPane;

    private ObservableList<NumberItem> originalList = FXCollections.observableArrayList();
    private List<Integer> currentArray = new ArrayList<>();

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

            currentArray.clear();
            Random rand = new Random();
            for (int i = 0; i < size; i++) {
                currentArray.add(rand.nextInt(high - low + 1) + low);
            }
            updateTableView(noSortedArrayableView, currentArray);
            SortedArrayableView1.getItems().clear();
            SortedArrayableView1.getColumns().clear();
            TextFieldGapn2.clear();
            TextFieldGapSubArray1.clear();
            TextFieldGapSubArray2.clear();
            TextFieldGapSubArray3.clear();

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
        int gap = n / 2;

        TextFieldGapn2.setText(String.valueOf(gap));
        TextFieldGapSubArray1.setText(getSubArrays(arrayToSort, gap));

        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = arrayToSort.get(i);
                int j = i;
                while (j >= gap && arrayToSort.get(j - gap) > temp) {
                    arrayToSort.set(j, arrayToSort.get(j - gap));
                    j -= gap;
                }
                arrayToSort.set(j, temp);
            }
            gap /= 2;
            if (gap == n / 4) {
                TextFieldGapSubArray2.setText(getSubArrays(arrayToSort, gap));
            } else if (gap == n / 8 && n > 8) {
                TextFieldGapSubArray3.setText(getSubArrays(arrayToSort, gap));
            }
        }

        updateTableView(SortedArrayableView1, arrayToSort);
    }

    private String getSubArrays(List<Integer> arr, int gap) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gap; i++) {
            List<Integer> subArray = new ArrayList<>();
            for (int j = i; j < arr.size(); j += gap) {
                subArray.add(arr.get(j));
            }
            sb.append("Subarray ").append(i).append(": ").append(subArray.stream().map(String::valueOf).collect(Collectors.joining(", "))).append(" | ");
        }
        return sb.toString().trim();
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
        TextFieldGapn2.clear();
        TextFieldGapSubArray1.clear();
        TextFieldGapSubArray2.clear();
        TextFieldGapSubArray3.clear();
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
