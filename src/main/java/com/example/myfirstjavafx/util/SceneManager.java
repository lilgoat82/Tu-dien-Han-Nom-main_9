package com.example.myfirstjavafx.util;

import com.example.myfirstjavafx.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SceneManager {

    private static final String STYLE_PATH = "/style.css";
    private static MainController mainController;

    public static void setMainController(MainController controller) {
        mainController = controller;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void switchScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(SceneManager.class.getResource(STYLE_PATH).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // If user click a hanzi, switch to "Tra Han" tab
    public static void switchTab(String query, String type) {
        System.out.println(mainController);
        if(mainController != null) {
            mainController.switchToHanTab(query);
        }
    }
}
