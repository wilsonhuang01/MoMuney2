package com.example.momuney2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PastTransactionsController {

    @FXML TextField         searchName;
    @FXML DatePicker        searchDate;
    @FXML ChoiceBox<String> searchCategory;
    @FXML ChoiceBox<String> sortBy;
    @FXML Button            pastTransactionsButton;

    @FXML
    private void initialize() {
        initializeUI();
    }

    private void initializeUI() {
        searchCategory.getItems().add("All");
        searchCategory.setValue("All");

        sortBy.getItems().addAll("Amount ↑", "Amount ↓", "Date ↑", "Date ↓");
        sortBy.setValue("Date ↑");
    }
}
