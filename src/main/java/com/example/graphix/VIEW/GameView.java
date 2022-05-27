package com.example.graphix.VIEW;

import com.example.graphix.Controllers.GameController;
import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Boss;
import com.example.graphix.Models.Bullet;
import com.example.graphix.Models.MyPlane;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.fxml.FXMLLoader;

import static com.example.graphix.HelloApplication.loadFXML;
import static com.example.graphix.HelloApplication.scene;

public class GameView {
    GameController gameController = new GameController();
    public void initialize(){
        MyPlane plane = gameController.createMyPlane();
        Boss boss = gameController.createBoss();
        //Parent root = loadFXML("gameMenu");
        gameController.createBackground();
        //gameController.createBullet(plane);
        Pane background = new Pane(gameController.background1, gameController.background2, gameController.weaponTypeImage, gameController.rocketState, gameController.HP, gameController.timer, gameController.BossHP, gameController.BossHpNumber, boss, gameController.Score);
        Group group = new Group(plane, background);
        plane.toFront();
        plane.bullet.setVisible(false);
        HelloApplication.scene.setRoot(group);
        gameController.backGroundInit();
        gameController.doThingBasedOnInput(HelloApplication.scene, plane, plane.bullet, group);
        gameController.setTimeTimer();
        boss.BossAnimation.start();
        gameController.checkCollision1(boss, plane, group);
        gameController.checkCollision2(boss, plane, group);
        gameController.bossShootAnimation(boss, group);
        boss.toFront();
    }





}
