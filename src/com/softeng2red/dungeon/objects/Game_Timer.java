package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.framework.Texture;

import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Game_Timer extends GameObject {

    Timer timer = new Timer();
    public int time;
    private int runtime = 1;
    private boolean running = false;

    public Game_Timer(float x, float y, ObjectId id) {
        super(x, y, id);

    }

    public void init() {
         time = GameObject.init_time;
    }

    public void start() {
        running = true;
        if (runtime == 1) {
            runtime++;
            timer.schedule(new TimerTask() {
                public void run() {
                    if (time > 0 && running) {
                        time--;
                    }
                    System.out.println("Time: " + time);
                }}, 500L, 1000L
            );
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int new_time) {
        time = new_time;
    }

    public void stop() {
        running = false;
    }


    public void tick(LinkedList<GameObject> object) {

    }

    public void render(Graphics g) {
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 96, 32);
    }
}
