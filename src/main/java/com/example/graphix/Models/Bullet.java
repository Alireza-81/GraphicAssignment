package com.example.graphix.Models;

import javafx.animation.AnimationTimer;

public class Bullet extends javafx.scene.image.ImageView{
    private int speed = 400;
    private int damage = 1;
    public AnimationTimer bulletAnimation;
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
