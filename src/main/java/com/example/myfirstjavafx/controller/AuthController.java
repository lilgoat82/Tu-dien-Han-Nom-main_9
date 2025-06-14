//package com.example.myfirstjavafx.controller;
//
//import com.example.myfirstjavafx.util.SceneManager;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import javafx.scene.Node;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class AuthController {
//
//    @FXML
//    private TextField usernameField;
//
//    @FXML
//    private PasswordField passwordField;
//
//    // Dữ liệu user mẫu
//    private final Map<String, String> users = new HashMap<>();
//
//    public AuthController() {
//        users.put("admin", "123456");
//        users.put("khoa", "123456");
//        users.put("test", "test");
//    }
//
//    @FXML
//    public void handleLogin(ActionEvent event) {
//        String username = usernameField.getText();
//        String password = passwordField.getText();
//
//        if (users.containsKey(username) && users.get(username).equals(password)) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/myfirstjavafx/fxml/MainView.fxml"));
//                Parent root = loader.load();
//
//                // Truyền tên người dùng về MainController
//                MainController controller = loader.getController();
//                controller.setLoggedinUser(username);
//
//                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                Scene scene = new Scene(root);
//                scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
//                stage.setScene(scene);
//                stage.show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Đăng nhập thất bại: sai tài khoản hoặc mật khẩu.");
//        }
//    }
//
//    @FXML
//    public void handleBack(ActionEvent event) {
//        SceneManager.switchScene(event, "/com/example/myfirstjavafx/fxml/MainView.fxml");
//    }
//}
