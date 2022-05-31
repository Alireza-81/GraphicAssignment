package com.example.graphix.VIEW;

import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Database;
import com.example.graphix.Models.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;



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
            message.setText("Register Successful");
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
            message.setText("no user exists with this username");
        } else if (!user.getPassword().equals(pass)) {
            System.out.println("password is incorrect");
            message.setText("password is incorrect");
        } else {
            System.out.println("user logged in successfully");
            message.setText("user logged in successfully");
            user.setLoggedIn(true);
            HelloApplication.changeMenu("Main-view");
        }
        message.setVisible(true);
        ok.setVisible(true);
    }
}