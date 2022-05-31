package com.example.graphix;

import com.example.graphix.VIEW.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    public static Scene scene;
    public static MediaPlayer mediaPlayer;
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = loadFXML("Register-view");
        RegisterController.setRoot(root);
        Scene scene = new Scene(root);
        HelloApplication.scene = scene;
        stage.setTitle("AP 3");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Media media = new Media(HelloApplication.class.getResource("Audio/Elevator-music.mp3").toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static Parent loadFXML(String name){
        try{
            URL address = new URL(HelloApplication.class.getResource("fxml/"+name+".fxml").toString());
            return FXMLLoader.load(address);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public static void changeMenu(String name){
        Parent root = loadFXML(name);
        HelloApplication.scene.setRoot(root);
       ;
    }

    public static void startGame(){

    }



    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        HelloApplication.scene = scene;
    }


}