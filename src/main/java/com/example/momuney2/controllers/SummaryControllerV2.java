package com.example.momuney2.controllers;

import com.example.momuney2.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SummaryControllerV2 {

    @FXML Button summaryButton;

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
