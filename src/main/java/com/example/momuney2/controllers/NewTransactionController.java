package com.example.momuney2.controllers;

import com.example.momuney2.DbConnection;
import com.example.momuney2.Main;
import com.example.momuney2.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewTransactionController {

    private final DbConnection connection = DbConnection.getInstance();

    private final User user = DbConnection.getInstance().getUser();

    @FXML Button            transactionsButton;
    @FXML Button            summaryButton;

    @FXML TextField         vendorName;
    @FXML TextField         vendorLocation;
    @FXML DatePicker        date;
    @FXML TextField         amount;
    @FXML ChoiceBox<String> category;
    @FXML Button            newCategoryButton;

    @FXML AnchorPane        newCategoryPane;
    @FXML TextField         newCategoryName;
    @FXML Button            newCategoryConfirmButton;
    @FXML Button            newCategoryCancelButton;

    @FXML
    private void initialize() {
        initializeUI();
    }

    private void initializeUI() {
        refreshCategories();
    }

    @FXML
    private void openNewCategoryPane() {
        newCategoryPane.setVisible(true);
    }

    @FXML
    private void closeNewCategoryPane() {
        newCategoryPane.setVisible(false);
    }

    @FXML
    private void createNewCategory() {
        String categoryName = newCategoryName.getText();
        if (categoryName == null || categoryName.trim().isEmpty()) {
            closeNewCategoryPane();
            return;
        }

        user.createCategory(categoryName);
        connection.updateUserData(user);

        closeNewCategoryPane();
        refreshCategories();
        category.setValue(categoryName);
    }

    private void refreshCategories() {
        category.getItems().clear();
        category.getItems().addAll(user.getCategories().keySet());
    }

    @FXML
    private void switchToTransactionsPage() {
        String transactionsPage = "PastTransactions.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(transactionsPage));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) transactionsButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Unable to find " + transactionsPage);
            throw new RuntimeException(e);
        }
    }
}
