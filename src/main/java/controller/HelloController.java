package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ucr.lab.laboratory8.HelloApplication;

import java.io.IOException;

public class HelloController {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private Text txtMessage;
    private void loadPage(String form) {
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
    void Exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void Home(ActionEvent event) {
        this.txtMessage.setText("Laboratory No. 8");
        this.bp.setCenter(ap);
    }

    @FXML
    void bubbleSortOnAction(ActionEvent event) {
        loadPage("bubbleSort.fxml");
    }

    @FXML
    void countingSortOnAction(ActionEvent event) {
        loadPage("countingSort.fxml");
    }

    @FXML
    void improvedBubbleSortOnAction(ActionEvent event) {
        loadPage("improvedBubbleSort.fxml");
    }

    @FXML
    void mergeSortOnAction(ActionEvent event) {
        loadPage("mergeSort.fxml");
    }

    @FXML
    void quickSortOnAction(ActionEvent event) {
        loadPage("quickSort.fxml");
    }

    @FXML
    void radixSortOnAction(ActionEvent event) {
        loadPage("radixSort.fxml");
    }

    @FXML
    void selectionSortOnAction(ActionEvent event) {
        loadPage("selectionSort.fxml");
    }

    @FXML
    void shellSortOnAction(ActionEvent event) {
        loadPage("shellSort.fxml");
    }

}