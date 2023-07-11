package lk.ijse.mychatapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatFormController extends Thread {

    @FXML
    private Label UserName;
    @FXML
    private TextField txtMsg;
    @FXML
    private Button sendButton;
    @FXML
    private VBox vbox;
    @FXML
    private Pane emojiPane;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    private FileChooser fileChooser;
    private File filePath;

    @FXML
    void sendOnAction(ActionEvent event) {
        String msg = txtMsg.getText();
        writer.println(UserName.getText() + ": " + msg);
        txtMsg.clear();

        if(msg.equalsIgnoreCase("!Bye") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);

        }
    }
    @FXML
    void enterSendOnAction(ActionEvent event) {
        sendButton.fire();
    }

    @FXML
    void photoSendOnAction(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(UserName.getText() + " " + "img" + filePath.getPath());
    }
    @FXML
    void emojiSendOnAction(MouseEvent event) {
        if (!emojiPane.isVisible()) {
            emojiPane.setVisible(true);
        } else {
            emojiPane.setVisible(false);
        }
    }

    public void initialize()  {
        String userName=LoginFormController.name;
        this.UserName.setText(userName);
        try {
            socket = new Socket("localhost", 5001);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            while (true) {


                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];


                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]+" ");
                }


                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }


                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);
                }


                if (firstChars.equalsIgnoreCase("img")) {

                    st = st.substring(3, st.length() - 1);


                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);


                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);


                    if (!cmd.equalsIgnoreCase(UserName.getText())) {

                        vbox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);


                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);


                    }

                    Platform.runLater(() -> vbox.getChildren().addAll(hBox));

                } else {
                    TextFlow tempFlow = new TextFlow();
                    if (!cmd.equalsIgnoreCase(UserName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);

                        tempFlow.setStyle("-fx-color: rgb(239,242,255);" +
                                "-fx-background-color: rgb(152,200,100);" +
                                " -fx-background-radius: 5px");
                        tempFlow.setPadding(new Insets(3,10,3,10));
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200);

                    TextFlow flow = new TextFlow(tempFlow);
                    HBox hBox = new HBox(12);

                    if (!cmd.equalsIgnoreCase(UserName.getText() + ":")) {
                        vbox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(flow);
                        hBox.setPadding(new Insets(2,5,2,10));

                    } else {
                        Text text2 = new Text(fullMsg + ": Me");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(flow2);
                        hBox.setPadding(new Insets(2,5,2,10));

                        flow2.setStyle("-fx-color: rgb(239,242,255);" +
                                "-fx-background-color: rgb(164,200,201);" +
                                "-fx-background-radius: 5px");
                        flow2.setPadding(new Insets(3,10,3,10));

                    }

                    Platform.runLater(() -> vbox.getChildren().addAll(hBox));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void happyOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE42");
    }

    public void coolOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE0E");
    }

    public void smileOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE02");
    }

    public void hugOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83E\uDD17");
    }

    public void hartOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDC96");
    }

    public void lovelyOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE0D");
    }

    public void sleepyOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE34");
    }

    public void supprisedOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE2E");
    }

    public void partyOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDC4D");
    }

    public void angryOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE21");
    }

    public void sadOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE1F");
    }

    public void cryOnAction(MouseEvent mouseEvent) {
        txtMsg.appendText("\uD83D\uDE2D");
    }
}