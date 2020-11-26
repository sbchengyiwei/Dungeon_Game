package com.softeng2red.dungeon.window;

import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.objects.Game_Timer;
import com.softeng2red.dungeon.objects.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class CameraTest {


    @Test
    public void cameraTest() {
        // if this test passes, the camera interact well with player as it follows the movement of player

        float initPosition;
        float currentPosition;

        // create camera object and player object
        Game_Timer game_timer = new Game_Timer(0,0, ObjectId.Game_Timer);
        Camera cam = new Camera(0,0);
        Handler handler = new Handler(cam, game_timer);
        Player player = new Player(100,100, handler, cam, ObjectId.Player);
        cam.tick(player);
        initPosition = cam.getX();

        // player moves
        player.setX(200);
        cam.tick(player);
        currentPosition = cam.getX();

        // check whether camera moves with player
        assertFalse(initPosition==currentPosition);






    }

}