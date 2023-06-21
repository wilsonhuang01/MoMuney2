package com.example.momuney2.controllers;

import com.example.momuney2.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;

public class SummaryControllerV2 {

    @FXML Button summaryButton;

    @FXML ChoiceBox<String> summaryOptions;
    @FXML ChoiceBox<String> chartOptions;
    @FXML DatePicker dateFrom;
    @FXML DatePicker dateTo;

    /*
        show summary for
                        spending by category
                            barchart & piechart (all-time or within time range)
                        spending by date
                            linechart & stacked barhcart (each category represent a line)
                            barchart (combined)
                        spending by vendor (name or location)
                            barchart & piechart (all time or within time range)
     */

    @FXML
    private void initialize() {
        initializeUI();
    }

    private void initializeUI() {
        summaryOptions.getItems().addAll("Vendor (Name)", "Vendor (Location)", "Category", "Date");
        summaryOptions.setValue("Category");
    }

    @FXML
    private void changeToNewTransactionPage() {
        String transactionsPage = "NewTransaction.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(transactionsPage));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) summaryButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Unable to find " + transactionsPage);
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void changeToTransactionsPage() {
        String transactionsPage = "PastTransactions.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(transactionsPage));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) summaryButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Unable to find " + transactionsPage);
            throw new RuntimeException(e);
        }
    }
}
