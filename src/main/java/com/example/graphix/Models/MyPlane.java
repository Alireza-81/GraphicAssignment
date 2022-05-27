package com.example.graphix.Models;

import java.util.ArrayList;

public class MyPlane extends javafx.scene.image.ImageView {
    public Bullet bullet = new Bullet();
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>(1000);
    public ArrayList<Bomb> bombs = new ArrayList<Bomb>(1000);
    private int speed = 300;
    private int score = 0;
    private int HP = 10;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
