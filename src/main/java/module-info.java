module com.example.momuney2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    exports com.example.momuney2;
    opens com.example.momuney2 to javafx.fxml;
    exports com.example.momuney2.models;
    opens com.example.momuney2.models to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.example.momuney2.controllers;
    opens com.example.momuney2.controllers to javafx.fxml, com.fasterxml.jackson.databind;
}