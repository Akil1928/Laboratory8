package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.lab.laboratory8.HelloApplication;

import java.io.IOException;

public class HelloController {
    @FXML
    private void load(String form) {
        try {
            // Intenta cargar el FXML con una ruta completa
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/"+form));

            // Asegúrate de manejar cualquier error de carga
            if (fxmlLoader.getLocation() == null) {
                System.err.println("No se puede encontrar el archivo FXML: " + form);
                return;
            }

            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace(); // Muestra el stack trace completo
            throw new RuntimeException("Error al cargar el FXML: " + form, e);
        }
    }

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Text txtMessage;

    @FXML
    void Exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void Home(ActionEvent event) {
        txtMessage.setText("Laboratory No. 3");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(txtMessage);
    }

    @FXML
    void bubbleSortOnAction(ActionEvent event) {
        load("bubbleSorting.fxml");
    }

    @FXML
    void countingSortOnAction(ActionEvent event) {
        load("countingSorting.fxml");
    }

    @FXML
    void impBubbleSortOnAction(ActionEvent event) {
        load("improvedBubbleSorting.fxml");
    }

    @FXML
    void mergeSortOnAction(ActionEvent event) {
        load("mergeSorting.fxml");
    }

    @FXML
    void quickSortOnAction(ActionEvent event) {
        load("quickSorting.fxml");
    }

    @FXML
    void radixSortOnAction(ActionEvent event) {
        load("radixSorting.fxml");
    }

    @FXML
    void selectionSortOnAction(ActionEvent event) {
        load("selectioSorting.fxml");
    }

    @FXML
    void shellSortOnAction(ActionEvent event) {
        load("shellSorting.fxml");
    }

}
