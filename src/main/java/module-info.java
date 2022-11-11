module kwong.inventoryapplication {
    requires javafx.controls;
    requires javafx.fxml;

    opens kwong.inventoryapplication to javafx.fxml;
    exports kwong.inventoryapplication;
    exports model;
    opens model to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}