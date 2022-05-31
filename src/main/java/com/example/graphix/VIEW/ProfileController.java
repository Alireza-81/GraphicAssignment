package com.example.graphix.VIEW;

import com.example.graphix.Controllers.ProfileControl;
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



    public void applyChanges(){
        boolean first = ProfileControl.changePassword(newPassword, passError);
        boolean second = ProfileControl.changeUsername(newUsername, userError);
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
