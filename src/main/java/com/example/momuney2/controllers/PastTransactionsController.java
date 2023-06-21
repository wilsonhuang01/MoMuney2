package com.example.momuney2.controllers;

import com.example.momuney2.DbConnection;
import com.example.momuney2.Main;
import com.example.momuney2.models.Transaction;
import com.example.momuney2.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PastTransactionsController {

    private final User user = DbConnection.getInstance().getUser();
    private List<Transaction> transactions = user.getTransactions();
    private final FilterService filterService = new FilterService(transactions);

    @FXML Button            newTransactionButton;
    @FXML Button            summaryButton;

    @FXML TextField         searchName;
    @FXML DatePicker        searchDateFrom;
    @FXML DatePicker        searchDateTo;
    @FXML ChoiceBox<String> searchCategory;
    @FXML ChoiceBox<String> sortBy;
    @FXML VBox              scrollBox;
    @FXML Label             resultCount;

    @FXML
    private void initialize() {
        initializeUI();
    }

    private void initializeUI() {
        searchCategory.getItems().add("All");
        searchCategory.getItems().addAll(user.getCategories().keySet());
        searchCategory.setValue("All");

        sortBy.getItems().addAll("Amount\t↑", "Amount\t↓", "Date\t\t↑", "Date\t\t↓");
        sortBy.setValue("Date\t\t↑");

        resultCount.setText(transactions.size() + " results");

        refreshTransactions();
    }

    private void addTransactionBoxes() {
        for (Transaction transaction : transactions) {
            scrollBox.getChildren().add(makeNewTransactionBox(transaction));
        }
    }

    private VBox makeNewTransactionBox(Transaction transaction) {
        VBox vBox = new VBox();
        vBox.setPrefSize(630, 31);
        vBox.setPadding(new Insets(0, 10, 0, 10));

        HBox hBox = new HBox();
        hBox.setPrefSize(610, 30);
        Insets margin = new Insets(0, 5, 0, 0);

        Label store = new Label();
        store.setPrefSize(170, 30);
        store.setText(transaction.getVendor().getName());
        HBox.setMargin(store, margin);
        hBox.getChildren().add(store);

        Label location = new Label();
        location.setPrefSize(140, 30);
        location.setText(transaction.getVendor().getLocation());
        HBox.setMargin(location, margin);
        hBox.getChildren().add(location);

        Label amount = new Label();
        amount.setPrefSize(80, 30);
        amount.setText("$" + transaction.getAmount());
        HBox.setMargin(amount, margin);
        hBox.getChildren().add(amount);

        Label category = new Label();
        category.setPrefSize(150, 30);
        category.setText(transaction.getCategory());
        HBox.setMargin(category, margin);
        hBox.getChildren().add(category);

        Label date = new Label();
        date.setPrefSize(80, 30);
        date.setText(transaction.getDate().toString());
        HBox.setMargin(date, margin);
        hBox.getChildren().add(date);

        Line line = new Line();
        line.setEndX(608);
        line.setStrokeWidth(2);
        line.setStyle("-fx-stroke: gray-color");

        vBox.getChildren().addAll(hBox, line);
        return vBox;
    }

    @FXML
    private void filter() {
        transactions = user.getTransactions();
        filterService.reset(transactions);

        filterService.filterByName(searchName.getText().split(" "));
        filterService.filterByDate(searchDateFrom.getValue(), searchDateTo.getValue());
        filterService.filterByCategory(searchCategory.getValue());

        transactions = filterService.getTransactions();
        refreshTransactions();
    }

    @FXML
    private void sort() {
        String sortingMethod = sortBy.getValue();
        if (sortingMethod == null) return;

        switch (sortingMethod) {
            case "Amount\t↑" -> transactions.sort((a, b) -> (int) Math.floor(a.getAmount() - b.getAmount()));
            case "Amount\t↓" -> transactions.sort((a, b) -> (int) Math.floor(b.getAmount() - a.getAmount()));
            case "Date\t\t↑" -> transactions.sort((a, b) -> a.getDate().compareTo(b.getDate()));
            case "Date\t\t↓" -> transactions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
            default -> {
            }
        }

        refreshTransactions();
    }

    private void refreshTransactions() {
        scrollBox.getChildren().clear();
        addTransactionBoxes();

        resultCount.setText(transactions.size() + " results");
    }

    @FXML
    private void changeToNewTransactionPage() {
        String transactionsPage = "NewTransactions.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(transactionsPage));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) newTransactionButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Unable to find " + transactionsPage);
            throw new RuntimeException(e);
        }
    }
}
