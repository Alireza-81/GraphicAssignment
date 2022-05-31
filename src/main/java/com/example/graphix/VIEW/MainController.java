package com.example.graphix.VIEW;

import com.example.graphix.VIEW.GameView;
import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Database;
import javafx.scene.control.Button;

public class MainController {
    public Button newGame;
    public Button loadGame;
    public Button profileView;
    public Button leaderBoard;
    public Button exitGame;

    public void backToRegister(){
        HelloApplication.changeMenu("Register-view");
        Database.getLoggedInUser().setLoggedIn(false);
    }

    public void goToProfileMenu(){
        HelloApplication.changeMenu("Profile-view");
    }

    public void startGame(){
        GameView gameView = new GameView();
        gameView.initialize();
        HelloApplication.mediaPlayer.stop();
    }



}
