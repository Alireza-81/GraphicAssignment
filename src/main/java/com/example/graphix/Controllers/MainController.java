package com.example.graphix.Controllers;

import com.example.graphix.GameView;
import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Database;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;

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
    }



}
