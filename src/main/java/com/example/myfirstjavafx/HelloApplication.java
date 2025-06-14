package com.example.myfirstjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/myfirstjavafx/fxml/MainView.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Từ điển Hán-Nôm");

        stage.getIcons().add(new Image("com/example/myfirstjavafx/assets/desktopIcon.png"));

        stage.setScene(scene);

//        stage.setResizable(false);
        stage.show();
    }
}