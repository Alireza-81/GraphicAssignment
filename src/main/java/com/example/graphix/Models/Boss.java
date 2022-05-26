package com.example.graphix.Models;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Boss extends javafx.scene.image.ImageView{
    private int HP = 50;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    private int speed = 300;
    public AnimationTimer BossAnimation;

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
