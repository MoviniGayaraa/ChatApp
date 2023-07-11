package lk.ijse.mychatapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginFormController{
    public static String name;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button buttonJoin;
    @FXML
    private TextField txtName;

    public void loginOnAction(ActionEvent event) throws IOException {
        clientLogin();
    }


    public void clientLogin()  throws IOException {
        name = txtName.getText();
        if (Pattern.matches("^[a-zA-Z\\s]+", txtName.getText())) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ChatForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

            txtName.clear();

        }else {
            Alert alert= new Alert(Alert.AlertType.ERROR,"please enter your name!");
            alert.show();
        }
    }

    public void nameOnAction(ActionEvent event) {
        buttonJoin.fire();
    }
}