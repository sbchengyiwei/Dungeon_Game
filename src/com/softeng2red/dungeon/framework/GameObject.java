package com.softeng2red.dungeon.framework;

import com.softeng2red.dungeon.objects.Finishing_Screen;

import java.awt.*;
import java.util.LinkedList;

//This is an abstract class which all objects will inherit from.
public abstract class GameObject {

    protected float x, y;
    protected ObjectId id;
    protected float velX = 0, velY = 0;
    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean on_m_block = false;
    protected boolean m_Right = false;
    protected boolean m_Left = false;
    protected boolean m_Up = false;
    protected boolean m_Down = false;
    public static int init_time = 80;
    public static int init_health = 2;
    public static int init_beer = 1;
    public static int healthNum = init_health;
    public static int beerNum = init_beer;
    public int maxHealth  = 4;
    public int minHealth  = 0;
    public int img_type = 0;



    public GameObject(float x, float y, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> object); // collision
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public boolean is_m_Up() {
        return m_Up;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public ObjectId getId() {
        return id;
    }



}
