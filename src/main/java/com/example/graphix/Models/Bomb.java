package com.example.graphix.Models;

import javafx.animation.AnimationTimer;

public class Bomb extends javafx.scene.image.ImageView{
    private int speed = 300;
    private int damage = 2;
    public AnimationTimer bombAnimation;
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
