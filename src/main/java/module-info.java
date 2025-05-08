module ucr.lab.laboratory8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.lab.laboratory8 to javafx.fxml;
    exports ucr.lab.laboratory8;
    exports controller;
    opens controller to javafx.fxml;
}