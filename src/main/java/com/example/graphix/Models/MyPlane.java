package com.example.graphix.Models;

import java.util.ArrayList;

public class MyPlane extends javafx.scene.image.ImageView {
    public Bullet bullet = new Bullet();
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>(1000);
    public ArrayList<Bomb> bombs = new ArrayList<Bomb>(1000);
    private int speed = 300;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
