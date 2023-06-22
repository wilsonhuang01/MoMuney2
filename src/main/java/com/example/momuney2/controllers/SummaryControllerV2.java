package com.example.momuney2.controllers;

import com.example.momuney2.DbConnection;
import com.example.momuney2.Main;
import com.example.momuney2.models.Category;
import com.example.momuney2.models.Transaction;
import com.example.momuney2.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SummaryControllerV2 {

    private final User user = DbConnection.getInstance().getUser();
    private List<Transaction> transactions = user.getTransactions();
    private final FilterService filterService = new FilterService(transactions);

    @FXML AnchorPane                rootPane;
    @FXML Button                    summaryButton;

    @FXML ChoiceBox<String>         summaryOptions;
    @FXML ChoiceBox<String>         chartOptions;
    @FXML DatePicker                dateFrom;
    @FXML DatePicker                dateTo;

    @FXML BarChart<String, Number>  categoryBarchart;
    @FXML PieChart                  categoryPiechart;

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

        updateChartOptions();
        chartOptions.setValue("Barchart");
    }

    @FXML
    private void updateChartOptions() {
        chartOptions.getItems().clear();

        switch (summaryOptions.getValue()) {
            case "Vendor (Name)", "Vendor (Location)", "Category" -> chartOptions.getItems().addAll("Barchart", "Piechart");
            case "Date" -> chartOptions.getItems().addAll("Barchart", "Linechart");
        }
    }

    @FXML
    private void createChart() {
        switch (summaryOptions.getValue()) {
            /*
            case "Vendor (Name)" -> {

            }
            case "Vendor (Location)" -> {

            }*/
            case "Category" -> {
                switch (chartOptions.getValue()) {
                    case "Barchart" -> createCategoryBarchart();
                    case "Piechart" -> createCategoryPiechart();
                }
            }
            /*
            case "Date" -> {

            }*/
        }
    }

    private void createCategoryBarchart() {
        System.out.println("Creating Category Barchart");
        categoryPiechart.setVisible(false);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");
        xAxis.getCategories().addAll(user.getCategories().keySet());

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Spending");

        categoryBarchart.setTitle("Spending by Category");
        categoryBarchart.setLegendVisible(false);
        categoryBarchart.getData().clear();

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        for (Category category : user.getCategories().values()) {
            series1.getData().add(new XYChart.Data<>(category.getName(), category.getSpending()));
        }
        categoryBarchart.getData().add(series1);

        categoryBarchart.setVisible(true);
    }

    private void createCategoryPiechart() {
        System.out.println("Creating Category Piechart");
        categoryBarchart.setVisible(false);

        categoryPiechart.setTitle("Spending by Category");
        categoryPiechart.setLegendVisible(false);
        categoryPiechart.getData().clear();
        rootPane.layout(); // force AnchorPane to lay out its children

        for (Category category : user.getCategories().values()) {
            PieChart.Data data = new PieChart.Data(category.getName() + ": $" + category.getSpending(), category.getSpending());
            categoryPiechart.getData().add(data);
        }

        categoryPiechart.setVisible(true);
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
