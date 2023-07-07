package lk.ijse.mychatapp.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    public static String name;
    public TextField txtUserName;
    public Button btnLogin;

    public void enterOnAction(ActionEvent actionEvent) {
        btnLogin.fire();
    }

    public void clickOnAction(ActionEvent actionEvent) throws IOException {
        name = txtUserName.getText();

        Parent root = FXMLLoader.load(getClass().getResource("/view/clientForm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("My Chat APP");
        //stage.getIcons().add(new Image("/img/icons8-message-preview-64.png"));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        txtUserName.clear();
    }
}
