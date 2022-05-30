package com.example.graphix.Controllers;

import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Database;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController {
    public Button next;
    public TextField password;
    public Label error;
    public TextField newPassword;
    public TextField newUsername;
    public Label userError;
    public Label passError;
    public Button removeAccount;

    public void goToNextStep(){
        if(password.getText().equals(Database.getLoggedInUser().getPassword())){
            error.setText("");
            HelloApplication.changeMenu("Profile2-view");
        } else {
            error.setText("Invalid Password");
        }
    }

    public boolean changeUsername(){
        if (newUsername.getText().equals(Database.getLoggedInUser().getUsername())) {
            userError.setText("new username cant be the same as the current one!");
            return false;
        } else if (newUsername.getText().isBlank()) {
            userError.setText("username can't be blank");
            return false;
        } else {
            userError.setText("");
            return true;
        }
    }

    public boolean changePassword(){
        if (newPassword.getText().equals(Database.getLoggedInUser().getPassword())) {
            passError.setText("new password can't be the same as the current one!");
            return false;
        } else if (newPassword.getText().isBlank()){
            passError.setText("password can't be blank");
            return false;
        }
        else {
            passError.setText("");
            return true;
        }
    }

    public void applyChanges(){
        boolean first = changePassword();
        boolean second = changeUsername();
        if(first && second) {
            Database.getLoggedInUser().setPassword(newPassword.getText());
            Database.getLoggedInUser().setUsername(newUsername.getText());
        }
    }

    public void removeAccount(){
        Database.removeUser(Database.getLoggedInUser());
        HelloApplication.changeMenu("Register-view");
    }

    public void goToMainMenu(){
        HelloApplication.changeMenu("Main-view");
    }


}
