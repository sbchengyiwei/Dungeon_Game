package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.window.Camera;
import com.softeng2red.dungeon.window.Handler;
import org.junit.Test;
import static org.junit.Assert.*;

public class VillainTest {

    @Test
    public void healthDecreaseTest(){
        // if this test is passed, the interaction between villain and player is perfectly done

        int initHealthNum;
        GameObject health = null;

        // create a player object and a villain object
        Game_Timer game_timer = new Game_Timer(0,0, ObjectId.Game_Timer);
        Camera cam = new Camera(0,0);
        Handler handler = new Handler(cam, game_timer);
        handler.addObject(new Villain(100,100, 2, ObjectId.Villain));
        handler.addObject(new Health(650 ,20, handler,ObjectId.Health));
        Player player = new Player(100,100, handler, cam, ObjectId.Player);  // place the player against the beer
        for(int i=0; i<handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Health) {
                health = tempObject;
            }
        }
        initHealthNum = health.healthNum;

        // let the player collides into a villain
        player.Collision(handler.object);
        // then check whether the health changed or not
        assertEquals(initHealthNum-1, health.healthNum);
    }
}