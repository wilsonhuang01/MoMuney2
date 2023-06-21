package com.example.momuney2.controllers;

import com.example.momuney2.DbConnection;
import com.example.momuney2.Main;
import com.example.momuney2.models.Date;
import com.example.momuney2.models.Transaction;
import com.example.momuney2.models.User;
import com.example.momuney2.models.Vendor;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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
    private void createNewTransaction() {
        Vendor transactionVendor;
        try {
            transactionVendor = processVendor();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
            return;
        }

        Date transactionDate;
        try {
            transactionDate = processDate();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
            return;
        }

        double transactionAmount;
        try {
            transactionAmount = processAmount();
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        String transactionCategory;
        try {
            transactionCategory = processCategory();
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        Transaction transaction = new Transaction(transactionVendor, transactionCategory, transactionDate, transactionAmount);
        user.addTransaction(transaction);
        connection.updateUserData(user);

        clearFields();
    }

    private Vendor processVendor() {
        String name = vendorName.getText(), location = vendorLocation.getText();
        if (name == null) throw new NullPointerException("Name is null");
        if (location == null) throw new NullPointerException("Location is null");

        if (name.trim().isEmpty()) name = "N/A";
        if (location.trim().isEmpty()) location = "N/A";

        return new Vendor(name, location);
    }

    private Date processDate() {
        LocalDate localDate = date.getValue();
        if (localDate == null) throw new NullPointerException("Date is null");

        return new Date(localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getYear());
    }

    private double processAmount() {
        String amountString = amount.getText();
        if (amountString == null) throw new NullPointerException("Amount is null");
        if (amountString.trim().isEmpty() || !isNumeric(amountString)) throw new IllegalArgumentException("Invalid amount");

        try {
            return round(amountString, 2);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }

        return -1;
    }

    private boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // code taken from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    private double round(String value, int places) {
        if (places < 0) throw new IllegalArgumentException("Places is less than 0");

        return new BigDecimal(value).setScale(places, RoundingMode.UP).doubleValue();
    }

    private String processCategory() {
        String categoryString = category.getValue();
        if (categoryString == null) throw new NullPointerException("Category is null");
        if (!user.getCategories().containsKey(categoryString)) throw new IllegalArgumentException("Invalid category");

        return categoryString;
    }

    private void clearFields() {
        vendorName.clear();
        vendorLocation.clear();
        date.setValue(null);
        amount.clear();
        category.setValue(null);
    }

    @FXML
    private void changeToTransactionsPage() {
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
