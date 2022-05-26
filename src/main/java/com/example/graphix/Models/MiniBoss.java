package com.example.graphix.Models;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public class MiniBoss extends javafx.scene.image.ImageView {
    private int HP = 2;
    private int speed = 200;
    private int damage = 1;
    public LongProperty miniBossReleaseTime = new SimpleLongProperty();
    public AnimationTimer miniBossAnimation;

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
