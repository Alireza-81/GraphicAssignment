package com.example.graphix.Controllers;

import com.example.graphix.HelloApplication;
import com.example.graphix.Models.*;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GameController {
    @FXML
    public ImageView background1;
    @FXML
    public ImageView background2;
    int BACKGROUND_WIDTH = 1280;
    ParallelTransition parallelTransition;

    final DoubleProperty bulletVelocityY = new SimpleDoubleProperty();
    final DoubleProperty bulletVelocityX = new SimpleDoubleProperty();
    final DoubleProperty planeVelocityY = new SimpleDoubleProperty();
    final DoubleProperty planeVelocityX = new SimpleDoubleProperty();
    final DoubleProperty bombVelocityY = new SimpleDoubleProperty();
    final DoubleProperty miniBossVelocity = new SimpleDoubleProperty();
    static int bulletNum = 0;
    static int bombNum = 0;
    int weaponType = 0;
    public ImageView weaponTypeImage = new ImageView();
    public ProgressBar rocketState = new ProgressBar();
    public ProgressBar HP = new ProgressBar();
    public ProgressBar BossHP = new ProgressBar();
    public Label BossHpNumber = new Label();
    public Label Score = new Label();
    public Label timer = new Label();
    public ArrayList<MiniBoss> miniBosses = new ArrayList<>();
    public int minutes = 10;
    public int seconds = 0;


    public void setTimeTimer(){
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final double[] secondsSinceStart = {0};
        final AnimationTimer setTime = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0;
                    secondsSinceStart[0] += elapsedSeconds;
                    if (secondsSinceStart[0] >=1) {
                        if (seconds == 0) {
                            minutes -= 1;
                            seconds = 59;
                        } else {
                            seconds -= 1;
                        }
                        String timeLeft = minutes + ":" + seconds;
                        timer.setText(timeLeft);
                        secondsSinceStart[0] = 0;
                    }
                }
                lastUpdateTime.set(timestamp);

            }
        };
        setTime.start();
    }




    public AnimationTimer movePlaneOnKeyPressY(Scene scene, final MyPlane plane) {
        final double planeSpeed = plane.getSpeed();

        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final AnimationTimer planeAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0 ;
                    final double deltaY = elapsedSeconds * planeVelocityY.get();
                    final double newY = Math.max(Math.min(620,plane.getTranslateY() + deltaY), 25);
                    plane.setTranslateY(newY);
                    //plane.bullet.setTranslateY(newY);
                }
                lastUpdateTime.set(timestamp);
            }
        };
        return  planeAnimation;
    }

    public AnimationTimer moveBulletWithPlaneY(Scene scene, MyPlane plane){
        final double bulletSpeed = plane.getSpeed();
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final AnimationTimer bulletAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0 ;
                    final double deltaY = elapsedSeconds * planeVelocityY.get();
                    plane.bullet.setTranslateY(plane.bullet.getTranslateY() + deltaY);
                }
                lastUpdateTime.set(timestamp);
            }
        };
        return bulletAnimation;
    }

    public AnimationTimer movePlaneOnKeyPressX(Scene scene, final MyPlane plane) {
        final double planeSpeed = plane.getSpeed();
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final AnimationTimer planeAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0 ;
                    final double deltaX = elapsedSeconds * planeVelocityX.get();
                    final double newX = Math.max(Math.min(1200,plane.getTranslateX() + deltaX), 25);
                    plane.setTranslateX(newX);
                    //plane.bullet.setTranslateX(newX);
                }
                lastUpdateTime.set(timestamp);
            }
        };
        return planeAnimation;
    }

    public AnimationTimer shootBulletOnKeyPress(Scene scene, Bullet bullet, MyPlane plane){
        final double bulletSpeed = bullet.getSpeed();
        final LongProperty lastUpdateTime1 = new SimpleLongProperty();
        final AnimationTimer bulletAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime1.get() > 0) {
                    final double elapsedSeconds = (timestamp - lastUpdateTime1.get()) / 1_000_000_000.0;
                    final double deltaX = elapsedSeconds * bulletVelocityX.get();
                    final double newX = Math.min(bullet.getTranslateX() + deltaX, 1280 );
                    bullet.setTranslateX(newX);
                }
                lastUpdateTime1.set(timestamp);
            }
        };
        return bulletAnimation;
    }

    public AnimationTimer dropBombOnKeyPress(Scene scene, Bomb bomb, MyPlane plane){
        final double bombSpeed = bomb.getSpeed();
        final LongProperty lastUpdateTime1 = new SimpleLongProperty();
        final AnimationTimer bombAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime1.get() > 0) {
                    final double elapsedSeconds = (timestamp - lastUpdateTime1.get()) / 1_000_000_000.0;
                    final double deltaY = elapsedSeconds * bombVelocityY.get();
                    final double newY = Math.min(bomb.getTranslateY() + deltaY, 720 );
                    bomb.setTranslateY(newY);
                }
                lastUpdateTime1.set(timestamp);
            }
        };
        return bombAnimation;
    }

    public AnimationTimer removeBulletsThatReachedDest(Scene scene, MyPlane plane, Group group){
        final LongProperty lastUpdateTime1 = new SimpleLongProperty();
        final AnimationTimer removeAnimation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(lastUpdateTime1.get() > 0){
                    if (plane.bullets.size() != 0){
                        for (int i = 0; i < plane.bullets.size() ; i++) {
                            if (plane.bullets.get(i).getTranslateX() > 1270) {
                                plane.bullets.get(i).bulletAnimation.stop();
                                group.getChildren().remove(plane.bullets.get(i));
                                plane.bullets.remove(plane.bullets.get(i));
                                bulletNum -= 1;
                            }
                        }
                    }
                    if (plane.bombs.size() != 0){
                        for (int i = 0;  i < plane.bombs.size(); i++){
                            if (plane.bombs.get(i).getTranslateY() > 700){
                                plane.bombs.get(i).bombAnimation.stop();
                                group.getChildren().remove(plane.bombs.get(i));
                                plane.bombs.remove(plane.bombs.get(i));
                                bombNum -= 1;
                            }
                        }
                    }
                }
                lastUpdateTime1.set(l);
            }
        };
        return removeAnimation;
    }

    public AnimationTimer removeMiniBossAndAdd(ArrayList<MiniBoss> miniBosses, Group group){
        final LongProperty lastUpdateTime1 = new SimpleLongProperty();
        final AnimationTimer removeAndAddAnimation = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lastUpdateTime1.get() > 0) {
                    for (int i = 0; i < miniBosses.size() ;i++) {
                        if (miniBosses.get(i).getTranslateX() <= -10) {
                            if (i == 0) {
                                if (miniBosses.get(1).getTranslateX() == 1340) {
                                    MiniBoss miniBoss1 = createMiniBoss(1300, 360);
                                    miniBosses.get(0).miniBossAnimation.stop();
                                    group.getChildren().remove(miniBosses.get(0));
                                    miniBosses.set(0, miniBoss1);
                                    miniBosses.get(0).miniBossAnimation.start();
                                    group.getChildren().add(miniBosses.get(0));
                                    miniBosses.get(0).miniBossReleaseTime.set(0);
                                }
                            } else if (i == 1) {
                                MiniBoss miniBoss2 = createMiniBoss(1340, 420);
                                miniBosses.get(1).miniBossAnimation.stop();
                                group.getChildren().remove(miniBosses.get(1));
                                miniBosses.set(1, miniBoss2);
                                miniBosses.get(1).miniBossAnimation.start();
                                group.getChildren().add(miniBosses.get(1));
                                miniBosses.get(1).miniBossReleaseTime.set(0);
                            } else if (i == 2) {
                                MiniBoss miniBoss3 = createMiniBoss(1340, 300);
                                miniBosses.get(2).miniBossAnimation.stop();
                                group.getChildren().remove(miniBosses.get(2));
                                miniBosses.set(2, miniBoss3);
                                miniBosses.get(2).miniBossAnimation.start();
                                group.getChildren().add(miniBosses.get(2));
                                miniBosses.get(2).miniBossReleaseTime.set(0);
                            }
                        }
                    }
                }
                lastUpdateTime1.set(l);
            }
        };
        return  removeAndAddAnimation;

    }

    public AnimationTimer fillUpProgressBar(){
        final LongProperty lastUpdateTime1 = new SimpleLongProperty();
        final LongProperty lastUpdateTime2 = new SimpleLongProperty();
        final AnimationTimer progressAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime1.get() > 0) {
                    final double elapsedSeconds = (timestamp - lastUpdateTime1.get()) / 1_000_000_000.0;
                    final double elapsedSecondsInGame = (timestamp - lastUpdateTime2.get())/1_000_000_000.0;
                    rocketState.setProgress(Math.min(rocketState.getProgress() + elapsedSeconds / 10, 1));
                    if (rocketState.getProgress() == 1){
                        rocketState.getStyleClass().remove("progressBar");
                        rocketState.getStyleClass().add("progressBarFull");
                    } else {
                        rocketState.getStyleClass().remove("progressBarFull");
                        rocketState.getStyleClass().add("progressBar");
                    }
                }
                lastUpdateTime1.set(timestamp);
            }
        };
        return progressAnimation;
    }

    public AnimationTimer miniBossMovement(Scene scene, ArrayList<MiniBoss> miniBosses, Group group){

        final LongProperty lastUpdateTime = new SimpleLongProperty();
        if (miniBosses.size() == 0) {
            MiniBoss miniBoss1 = createMiniBoss(1300, 360);
            MiniBoss miniBoss2 = createMiniBoss(1340, 420);
            MiniBoss miniBoss3 = createMiniBoss(1340, 300);
            miniBosses.add(miniBoss1);
            miniBosses.add(miniBoss2);
            miniBosses.add(miniBoss3);
        }
        final AnimationTimer miniBossMovementAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0) {
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0;
                    for (MiniBoss miniBoss : miniBosses) {
                        if (miniBoss.miniBossReleaseTime.get()/100 >= 3) {
                            double deltaX = elapsedSeconds * miniBossVelocity.get();
                            double newX = miniBoss.getTranslateX() + deltaX;
                            miniBoss.setTranslateX(newX);
                        }
                    }

                }
                lastUpdateTime.set(timestamp);
                for (MiniBoss miniBoss : miniBosses) {
                    miniBoss.miniBossReleaseTime.set(miniBoss.miniBossReleaseTime.get() + 1);
                }
            }
        };
        return  miniBossMovementAnimation;
    }


    public AnimationTimer miniBossAnimation(Scene scene, MiniBoss miniBoss){
        URL address1 = null;
        URL address2 = null;
        URL address3 = null;
        URL address4 = null;
        try{
            address1 = new URL(HelloApplication.class.getResource("images/miniboss1.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        try{
            address2 = new URL(HelloApplication.class.getResource("images/miniboss2.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        try{
            address3 = new URL(HelloApplication.class.getResource("images/miniboss3.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        try{
            address4 = new URL(HelloApplication.class.getResource("images/miniboss4.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        Image image1 = new Image(address1.toString());
        Image image2 = new Image(address2.toString());
        Image image3 = new Image(address3.toString());
        Image image4 = new Image(address4.toString());
        miniBoss.setImage(image1);

        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final double[] secondsSinceStart = {0};
        final AnimationTimer miniBossAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0;
                    secondsSinceStart[0] += elapsedSeconds;
                    if (secondsSinceStart[0] >= 0.10) {
                        if (miniBoss.getImage().equals(image1)) {
                            miniBoss.setImage(image2);
                        } else if (miniBoss.getImage().equals(image2)) {
                            miniBoss.setImage(image3);
                        } else if (miniBoss.getImage().equals(image3)) {
                            miniBoss.setImage(image4);
                        } else if (miniBoss.getImage().equals(image4)) {
                            miniBoss.setImage(image1);
                        }
                        secondsSinceStart[0] = 0;
                    }
                }
                lastUpdateTime.set(timestamp);
            }
        };
        return miniBossAnimation;
    }

    public AnimationTimer BossAnimation(Boss boss){
        ArrayList<URL> address = new ArrayList<URL>();
        ArrayList<Image> image = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            try{
                URL temp = new URL(HelloApplication.class.getResource("images/Boss" + (i + 1) + ".png").toString());
                address.add(temp);
            } catch (IOException exception){
                exception.printStackTrace();
            }
            Image temp = new Image(address.get(i).toString());
            image.add(temp);
        }
        boss.setImage(image.get(1));

        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final double[] secondsSinceStart = {0};
        final AnimationTimer BossAnimation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                if (lastUpdateTime.get() > 0){
                    final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0;
                    secondsSinceStart[0] += elapsedSeconds;
                    if (secondsSinceStart[0] >= 0.14) {
                        for (int i = 0; i< 6; i++) {
                            if (boss.getImage().equals(image.get(i))) {
                                boss.setImage(image.get((i + 1) % 6));
                                boss.setEffect(null);
                                break;
                            }
                        }
                        secondsSinceStart[0] = 0;
                    }
                }
                lastUpdateTime.set(timestamp);
            }
        };
        return BossAnimation;

    }

    public void checkCollision1(Boss boss, MyPlane plane, Group group){
        final LongProperty lastUpdateTime = new SimpleLongProperty();
        final AnimationTimer collisionCheck = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lastUpdateTime.get() > 0){
                    for (int i = 0; i < plane.bullets.size(); i++){
                        if (plane.bullets.get(i).getTranslateX() + plane.bullets.get(i).getFitWidth() >= boss.getTranslateX() + 50
                                && plane.bullets.get(i).getTranslateX() + plane.bullets.get(i).getFitWidth() <= boss.getTranslateX() + boss.getFitWidth() - 50
                                && plane.bullets.get(i).getTranslateY() + plane.bullets.get(i).getFitHeight() >= boss.getTranslateY() + 50
                                && plane.bullets.get(i).getTranslateY() + plane.bullets.get(i).getFitHeight() <= boss.getTranslateY() + boss.getFitHeight() - 50){
                            plane.bullets.get(i).bulletAnimation.stop();
                            group.getChildren().remove(plane.bullets.get(i));
                            boss.setHP(boss.getHP() - plane.bullets.get(i).getDamage());
                            plane.bullets.remove(plane.bullets.get(i));
                            bulletNum -= 1;
                            bossReceiveDamageColorAdjust(boss);
                        }
                    }
                    for (int i = 0; i < plane.bombs.size(); i++){
                        if (plane.bombs.get(i).getTranslateX() + plane.bombs.get(i).getFitWidth() >= boss.getTranslateX() + 50
                                && plane.bombs.get(i).getTranslateX() + plane.bombs.get(i).getFitWidth() <= boss.getTranslateX() + boss.getFitWidth() - 50
                                && plane.bombs.get(i).getTranslateY() + plane.bombs.get(i).getFitHeight() >= boss.getTranslateY() + 50
                                && plane.bombs.get(i).getTranslateY() + plane.bombs.get(i).getFitHeight() <= boss.getTranslateY() + boss.getFitHeight() - 50){
                            plane.bombs.get(i).bombAnimation.stop();
                            group.getChildren().remove(plane.bombs.get(i));
                            boss.setHP(boss.getHP() - plane.bombs.get(i).getDamage());
                            plane.bombs.remove(plane.bombs.get(i));
                            bombNum -= 1;
                            bossReceiveDamageColorAdjust(boss);
                        }
                    }
                }
                lastUpdateTime.set(l);
            }
        };
        collisionCheck.start();
    }

    private void bossReceiveDamageColorAdjust(Boss boss) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(0.8);
        boss.setEffect(colorAdjust);
        BossHP.setProgress(2 * boss.getHP() / (double) 100);
        BossHpNumber.setText(" " + boss.getHP() * 2 + "% ");
        checkBossHp(boss);
    }

    public void checkBossHp(Boss boss){
        if (boss.getHP() <= 10){
            BossHP.getStyleClass().remove("HPFull");
            BossHP.getStyleClass().add("HPLow");
        } else {
            BossHP.getStyleClass().remove("HPLow");
            BossHP.getStyleClass().add("HPFull");
        }
    }






    public MyPlane createMyPlane(){
        final MyPlane myPlane = new MyPlane();
        URL address = null;
        try{
            address = new URL(HelloApplication.class.getResource("images/redPlane.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        Image image = new Image(String.valueOf(address));
        myPlane.setImage(image);
        myPlane.setFitWidth(100);
        myPlane.setPreserveRatio(true);
        return myPlane;
    }


    public void createBackground(){
        //========================
        BossHpNumber.setLayoutX(1120);
        BossHpNumber.setLayoutY(5);
        BossHpNumber.setText(" 100% ");
        BossHpNumber.getStylesheets().add(HelloApplication.class.getResource("css/GameController.css").toString());
        BossHpNumber.getStyleClass().add("BossHPNumber");
        //=========================
        BossHP.setProgress(1);
        BossHP.setLayoutX(1170);
        BossHP.setLayoutY(8);
        BossHP.getStylesheets().add(HelloApplication.class.getResource("css/GameController.css").toString());
        BossHP.getStyleClass().add("HPFull");
        //=========================
        timer.setLayoutX(110);
        timer.setLayoutY(692);
        timer.getStylesheets().add(HelloApplication.class.getResource("css/GameController.css").toString());
        timer.getStyleClass().add("timer");
        //=========================
        HP.setLayoutY(695);
        HP.setLayoutX(5);
        HP.setProgress(100);
        HP.getStylesheets().add(HelloApplication.class.getResource("css/GameController.css").toString());
        HP.getStyleClass().add("HPFull");
        //==========================
        weaponTypeImage.setLayoutX(5);
        weaponTypeImage.setLayoutY(5);
        weaponTypeImage.setFitWidth(25);
        weaponTypeImage.setFitHeight(25);
        URL address1 = null;
        try {
            address1 = new URL(HelloApplication.class.getResource("images/weapon0.png").toString());
        } catch (IOException e){
            e.printStackTrace();
        }
        Image weaponImage = new Image(address1.toString());
        weaponTypeImage.setImage(weaponImage);
        rocketState.setLayoutX(40);
        rocketState.setLayoutY(8);
        rocketState.setProgress(0);
        rocketState.getStylesheets().add(HelloApplication.class.getResource("css/GameController.css").toString());
        rocketState.getStyleClass().add("progressBar");

        background1 = new ImageView();
        background1.setFitHeight(720);
        background1.prefWidth(1280);
        background1.setFitWidth(1280);
        background2 = new ImageView();
        background2.setFitHeight(720);
        background2.setFitWidth(1280);
        background2.prefWidth(1280);
        background2.setLayoutX(1280);
        URL address = null;
        try{
            address = new URL(HelloApplication.class.getResource("images/field.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        Image image = new Image(address.toString());

        background1.setImage(image);
        background2.setImage(image);
    }

    public void backGroundInit(){
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(5000), background1);
        translateTransition.setFromX(0);
        translateTransition.setToX(-1 * BACKGROUND_WIDTH);
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 =
                new TranslateTransition(Duration.millis(5000), background2);
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-1 * BACKGROUND_WIDTH);
        translateTransition2.setInterpolator(Interpolator.LINEAR);
        parallelTransition = new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();
    }

    public void createBullet(MyPlane plane){
        Bullet bullet = new Bullet();
        bullet.setTranslateX(plane.getTranslateX() + 100);
        bullet.setTranslateY(plane.getTranslateY() + 30);
        URL address = null;
        try{
            address = new URL(HelloApplication.class.getResource("images/bullet.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        Image image = new Image(address.toString());
        bullet.setImage(image);
        bullet.setFitHeight(25);
        bullet.setFitWidth(25);
        plane.bullets.add(bullet);
    }

    public void createBomb(MyPlane plane){
        Bomb bomb = new Bomb();
        bomb.setTranslateX(plane.getTranslateX() + 50);
        bomb.setTranslateY(plane.getTranslateY() + 70);
        URL address = null;
        try{
            address = new URL(HelloApplication.class.getResource("images/bomb.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        Image image = new Image(address.toString());
        bomb.setImage(image);
        bomb.setFitHeight(50);
        bomb.setFitWidth(50);
        plane.bombs.add(bomb);
    }

    public MiniBoss createMiniBoss(double x, double y){
        MiniBoss miniBoss = new MiniBoss();
        miniBoss.setTranslateX(x);
        miniBoss.setTranslateY(y);
        URL address = null;
        try{
            address = new URL(HelloApplication.class.getResource("images/miniboss1.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        Image image = new Image(address.toString());
        miniBoss.setImage(image);
        miniBoss.setFitWidth(80);
        miniBoss.setFitHeight(50);
        AnimationTimer animation = miniBossAnimation(null, miniBoss);
        miniBoss.miniBossAnimation = animation;
        return miniBoss;
    }

    public Boss createBoss(){
        Boss boss = new Boss();
        boss.setTranslateX(800);
        boss.setTranslateY(210);
        boss.setFitHeight(300);
        boss.setFitWidth(350);
        URL address = null;
        try{
            address = new URL(HelloApplication.class.getResource("images/Boss1.png").toString());
        } catch (IOException exception){
            exception.printStackTrace();
        }
        Image image = new Image(address.toString());
        boss.setImage(image);
        AnimationTimer animationTimer = BossAnimation(boss);
        boss.BossAnimation = animationTimer;
        return boss;
    }


    public void doThingBasedOnInput(Scene scene, MyPlane plane, Bullet bullet, Group group){
        //=========================
        final boolean[] isPaused = {false};
        URL address = null;
        try {
            address = new URL(HelloApplication.class.getResource("Audio/telegram-cloud-document-4-723784306719980189.mpga").toString());
        } catch (IOException e){
            e.printStackTrace();
        }
        Media media = new Media(address.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        AnimationTimer playMusic = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (isPaused[0])
                    mediaPlayer.pause();
                else
                    mediaPlayer.play();
            }
        };
        playMusic.start();
        //=========================
        AnimationTimer movePlaneY = movePlaneOnKeyPressY(scene, plane);
        AnimationTimer movePlaneX = movePlaneOnKeyPressX(scene, plane);
        AnimationTimer removeBullets = removeBulletsThatReachedDest(scene, plane, group);
        AnimationTimer fillUpRocketState = fillUpProgressBar();
        AnimationTimer miniBossGroupMove = miniBossMovement(scene, miniBosses, group);
        miniBossGroupMove.start();
        AnimationTimer miniBossAnimation = miniBossAnimation(scene, miniBosses.get(0));
        AnimationTimer miniBossAnimation1 = miniBossAnimation(scene, miniBosses.get(1));
        AnimationTimer miniBossAnimation2 = miniBossAnimation(scene, miniBosses.get(2));
        AnimationTimer removeMiniBossAndAdd = removeMiniBossAndAdd(miniBosses, group);
        fillUpProgressBar().start();
        movePlaneY.start();
        movePlaneX.start();
        removeBullets.start();
        miniBossAnimation.start();
        miniBossAnimation1.start();
        miniBossAnimation2.start();
        removeMiniBossAndAdd.start();
        group.getChildren().add(miniBosses.get(0));
        group.getChildren().add(miniBosses.get(1));
        group.getChildren().add(miniBosses.get(2));

        miniBossVelocity.set(-200);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.UP){
                    planeVelocityY.set(-300);
                } else if (keyEvent.getCode() == KeyCode.DOWN){
                    bulletVelocityY.set(300);
                    planeVelocityY.set(300);
                } else if (keyEvent.getCode() == KeyCode.LEFT){
                    planeVelocityX.set(-300);
                }
                else if (keyEvent.getCode() == KeyCode.RIGHT){
                    planeVelocityX.set(300);
                }
                if(keyEvent.getCode() == KeyCode.SPACE){
                    if (weaponType == 0) {
                        createBullet(plane);
                        AnimationTimer shootBullet = shootBulletOnKeyPress(scene, plane.bullets.get(bulletNum), plane);
                        plane.bullets.get(bulletNum).bulletAnimation = shootBullet;
                        shootBullet.start();
                        group.getChildren().add(plane.bullets.get(bulletNum));
                        bullet.setVisible(true);
                        bulletVelocityX.set(400);
                        bulletNum += 1;
                    }
                    else if (weaponType == 1) {
                        createBomb(plane);
                        AnimationTimer dropBomb = dropBombOnKeyPress(scene, plane.bombs.get(bombNum), plane);
                        plane.bombs.get(bombNum).bombAnimation = dropBomb;
                        dropBomb.start();
                        group.getChildren().add(plane.bombs.get(bombNum));
                        bombVelocityY.set(300);
                        bombNum += 1;
                    }
                }
                if (keyEvent.getCode() == KeyCode.TAB){
                    if (weaponType == 0){
                        weaponType = 1;
                    } else if (weaponType == 1){
                        weaponType = 0;
                    }
                    URL address1 = null;
                    try {
                        address1 = new URL(HelloApplication.class.getResource("images/weapon" + weaponType + ".png").toString());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    Image weaponImage = new Image(address1.toString());
                    weaponTypeImage.setImage(weaponImage);
                }
                if (keyEvent.getCode() == KeyCode.M){
                    if (mediaPlayer.isMute())
                        mediaPlayer.setMute(false);
                    else
                        mediaPlayer.setMute(true);
                }
                if (keyEvent.getCode() == KeyCode.P){
                    if (isPaused[0])
                        isPaused[0] = false;
                    else
                        isPaused[0] = true;
                }
                if (keyEvent.getCode() == KeyCode.U){
                    mediaPlayer.play();
                }


            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.DOWN) {
                    planeVelocityY.set(0);
                } else if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.LEFT){
                    planeVelocityX.set(0);
                }
            }
        });


    }



}
