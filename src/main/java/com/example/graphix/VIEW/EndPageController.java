package com.example.graphix.VIEW;

import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Database;
import com.example.graphix.VIEW.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class EndPageController {
    @FXML
    public Text Score;
    @FXML
    public Text ElapsedTime;
    public Text Progress;

    public void setScore(){
        Score.setText("Score: " + Database.getLoggedInUser().getScore());
        ElapsedTime.setText("Elapsed Time " + (9- Database.getLoggedInUser().getMinutes()) + ":" + (60 - Database.getLoggedInUser().getSeconds()));
    }

    public void showProgress(){
        Progress.setText(Database.getLoggedInUser().getProgress() + "%");
    }


    public void startGame1(){
        GameView gameView = new GameView();
        gameView.initialize();
        Database.getLoggedInUser().setScore(0);
    }

    public void goToMainMenu(){
        Database.getLoggedInUser().setScore(0);
        HelloApplication.changeMenu("Main-view");

        HelloApplication.mediaPlayer.play();
    }
    public void goToProfileMenu(){
        Database.getLoggedInUser().setScore(0);
        Database.getLoggedInUser().setLoggedIn(false);
        HelloApplication.changeMenu("Register-view");
        HelloApplication.mediaPlayer.play();
    }
}
