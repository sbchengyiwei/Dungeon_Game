package com.softeng2red.dungeon.objects;

import com.softeng2red.dungeon.framework.GameObject;
import com.softeng2red.dungeon.framework.ObjectId;
import com.softeng2red.dungeon.window.Camera;
import com.softeng2red.dungeon.window.Game;
import com.softeng2red.dungeon.window.Handler;
import com.softeng2red.dungeon.window.HandlerTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class Game_OverTest {

    @Test
    public void gameOverTest() {
        // if this test passes, the game over screen class is complete and works well
        // when when game over screen is displayed, all objects in handler would be cleared except for Game_Over object

        // let healthNum = 0 and so the game will reach game-over stage, then check whether the handler contains only one object
        Game game = new Game();
        game.init();
        game.handler.addObject(new Obstacle(68,132, ObjectId.Obstacle));
        game.handler.addObject(new Obstacle(168,132, ObjectId.Obstacle));
        game.handler.addObject(new Obstacle(268,132, ObjectId.Obstacle));
        game.handler.addObject(new Obstacle(368,132, ObjectId.Obstacle));
        game.handler.addObject(new Health(650 ,20, game.handler,ObjectId.Health));
        for(int i=0; i<game.handler.object.size(); i++) {
            GameObject tempObject = game.handler.object.get(i);
            if(tempObject.getId() == ObjectId.Health) {
                tempObject.healthNum = 0;
            }
        }

        game.GameOver();
        int objectNum;
        objectNum = game.handler.object.size();

        assertEquals(1, objectNum);





    }

}