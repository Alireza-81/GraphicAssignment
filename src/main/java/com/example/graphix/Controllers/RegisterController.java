package com.example.graphix.Controllers;

import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Database;
import com.example.graphix.Models.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.VBox;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;

import static com.example.graphix.HelloApplication.loadFXML;


public class RegisterController {
    public TextField username;
    public TextField password;
    public Button registerButton;
    public Button loginButton;
    public Label message;
    public Button ok;
    public VBox messageBox;
    public static Parent parent;

    public static Parent getParent() {
        return parent;
    }

    public static void setRoot(Parent root) {
        RegisterController.parent = root;
    }

    public enum LoginMessage {
        SHORT_USERNAME("Username must be at least 6 characters!", Alert.AlertType.ERROR),
        SHORT_PASSWORD("Password must be at least 8 characters!", Alert.AlertType.ERROR),
        TAKEN_USERNAME("User with this username already exists!", Alert.AlertType.ERROR),
        TAKEN_NICKNAME("User with this nickname already exists!", Alert.AlertType.ERROR),
        NONIDENTICAL_PASSWORDS("Passwords are not the same!", Alert.AlertType.ERROR),
        EMPTY_FIELD("Fill the fields!", Alert.AlertType.ERROR),
        INCORRECT_USERNAME_PASSWORD("Username and password didn't match!", Alert.AlertType.ERROR),
        SUCCESSFUL_SIGN_UP("Sign-up was successful", Alert.AlertType.INFORMATION),
        SUCCESSFUL_LOGIN("Login was successful", Alert.AlertType.INFORMATION),
        LOGOUT_CONFIRMATION("Are you sure you want to logout?", Alert.AlertType.CONFIRMATION),
        EXIT_CONFIRMATION("Are you sure you want to exit?", Alert.AlertType.CONFIRMATION),
        ERROR_OCCURRED("Error occurred", Alert.AlertType.ERROR),
        INVALID_INPUT("your input can only contain characters and numbers", Alert.AlertType.ERROR),
        SUCCESS("",null),LOG_OUT_OTHER_ACCOUNTS("Please logout other accounts and then  try again", Alert.AlertType.ERROR) ;
        private final String label;
        private final Alert.AlertType alertType;

        LoginMessage(String label, Alert.AlertType alertType) {
            this.label = label;
            this.alertType = alertType;
        }

        public Alert.AlertType getAlertType() {
            return alertType;
        }

        public String getLabel() {
            return label;
        }
    }

    public class PopUpMessage {
        private static Stage stage;

        private Alert alert;

        public PopUpMessage(Alert.AlertType alertType, String label) {
            Alert alert;
            if (alertType.equals(Alert.AlertType.ERROR)) {
                alert = new Alert(Alert.AlertType.ERROR);
                initializeAlert(label, alert);
                setStyleForError(alert);
                setStyleForButton(alert);
                makeScreenBlur(alert);
                alert.show();
            } else if (alertType.equals(Alert.AlertType.INFORMATION)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                initializeAlert(label, alert);
                setStyleForInformationAndConfirmation(alert);
                setStyleForButton(alert);
                makeScreenBlur(alert);
                alert.show();
            } else if (alertType.equals(Alert.AlertType.CONFIRMATION)) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                initializeAlert(label, alert);
                setStyleForInformationAndConfirmation(alert);
                setStyleForButton(alert);
                makeScreenBlur(alert);
                alert.showAndWait();
            }
        }

        private void makeScreenBlur(Alert alert) {
            parent.setEffect(new GaussianBlur(20));
            alert.setOnCloseRequest(dialogEvent -> parent.setEffect(null));
        }

        private void initializeAlert(String label, Alert alert) {
            setAlert(alert);
            alert.setContentText(label);
            alert.initOwner(stage);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initStyle(StageStyle.TRANSPARENT);
        }

        private void setStyleForError(Alert alert) {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-background-color: #9e376c; -fx-font-family: \"Matrix II Regular\";");
            dialogPane.getScene().setFill(Color.TRANSPARENT);
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
        }

        private void setStyleForInformationAndConfirmation(Alert alert) {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setHeaderText(null);
            dialogPane.setGraphic(null);
            dialogPane.setStyle("-fx-border-radius: 10; -fx-border-color: #bb792d; -fx-border-width: 7; -fx-background-radius: 14; -fx-font-family: \"Matrix II Regular\"; -fx-background-color: #103188;");
            dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-line-spacing: 5px;");
            dialogPane.getScene().setFill(Color.TRANSPARENT);
        }

        private void setStyleForButton(Alert alert) {
            ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-radius: 10; -fx-background-color: #bb792d; -fx-font-size: 16; -fx-text-fill: white;"));
            buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        }

        public Alert getAlert() {
            return alert;
        }

        public static Stage getStage() {
            return stage;
        }

        public static void setStage(Stage stage) {
            PopUpMessage.stage = stage;
        }

        public static Parent getParent() {
            return parent;
        }


        public void setAlert(Alert alert) {
            this.alert = alert;
        }
    }
    @FXML
    private Label welcomeText;
    @FXML
    protected void onLaunch() {
        welcomeText.setText("");
    }

    @FXML
    protected void register(){
        String userN = username.getText();
        String pass = password.getText();
        if (Database.getUserByUsername(userN) != null) {
            message.setText("username exists!");
        } else {
            User user = new User(userN, pass);
            Database.addUser(user);
            LoginMessage message = LoginMessage.SUCCESSFUL_LOGIN;
            new PopUpMessage(message.getAlertType(), message.getLabel());
        }
        message.setVisible(true);
        ok.setVisible(true);

    }
    @FXML
    protected void disableVisibility(){
        message.setVisible(false);
        ok.setVisible(false);
    }

    @FXML
    protected void login(){
        String userN = username.getText();
        String pass = password.getText();
        User user = Database.getUserByUsername(userN);
        if (Database.getUserByUsername(userN) == null) {
            System.out.println("no user exists with this username");
        } else if (!user.getPassword().equals(pass)) {
            System.out.println("password is incorrect");
        } else {
            System.out.println("user logged in successfully");
            user.setLoggedIn(true);
            HelloApplication.changeMenu("Main-view");
        }
    }
}