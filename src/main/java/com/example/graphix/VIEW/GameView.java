package com.example.graphix.VIEW;

import com.example.graphix.Controllers.GameController;
import com.example.graphix.HelloApplication;
import com.example.graphix.Models.Boss;
import com.example.graphix.Models.Bullet;
import com.example.graphix.Models.MyPlane;
import javafx.scene.Group;
import javafx.scene.layout.Pane;


public class GameView {
    public void initialize(){
        GameController gameController = new GameController();
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
        gameController.doThingBasedOnInput(HelloApplication.scene, plane, plane.bullet, group, boss);
        System.out.println("boos");
    }


}
