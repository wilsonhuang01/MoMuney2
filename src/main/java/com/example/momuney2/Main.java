package com.example.momuney2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PastTransactions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to MoMuney!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}