package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.window.Camera;
import com.softeng2red.dungeon.window.Handler;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObstacleTest {

    @Test
    public void obstacleTest() {

        // create a player object and a villain object
        Game_Timer game_timer = new Game_Timer(0,0, ObjectId.Game_Timer);
        Camera cam = new Camera(0,0);
        Handler handler = new Handler(cam, game_timer);
        handler.addObject(new Obstacle(68,132, ObjectId.Obstacle));
        Player player = new Player(100,100, handler, cam, ObjectId.Player);  // place the player against the obstacle
        float initPosition = player.getX();

        // let player move to left
        player.setX(99);
        player.Collision(handler.object);  // if not call this method, player will not be blocked by obstacle and test will fail
        float currentPosition = player.getX();

        // check whether player is blocked by obstacle or not
        assertTrue(initPosition == currentPosition);


    }
}