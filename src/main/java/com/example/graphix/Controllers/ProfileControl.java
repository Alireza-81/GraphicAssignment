package com.example.graphix.Controllers;

import com.example.graphix.Models.Database;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileControl {

    public static boolean changeUsername(TextField newUsername , Label userError){
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

    public static boolean changePassword(TextField newPassword, Label passError){
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
}
